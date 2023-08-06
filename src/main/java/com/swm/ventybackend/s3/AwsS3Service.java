package com.swm.ventybackend.s3;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.swm.ventybackend.content_dynamo.ContentDynamo;
import com.swm.ventybackend.content_dynamo.ContentDynamoRepository;
import lombok.RequiredArgsConstructor;
import net.coobird.thumbnailator.Thumbnails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AwsS3Service {

    // DynamoDB 연동 로직 추가 (230729)
    // @TODO : REFACTORING - ContentController 기능을 가져다 씀.. 구조적 불합리성 개선 필요
    @Autowired
    private DynamoDBMapper dynamoDBMapper;

    @Value("${AWS_S3_BUCKET}")
    private String bucket;

    @Value("${AWS_S3_BUCKET_CONTENT_URL}")
    private String contentUrl;

    @Value("${AWS_S3_BUCKET_THUMBNAIL_URL}")
    private String thumbnailUrl;

    private final AmazonS3 amazonS3;


    public List<String> uploadFile(List<MultipartFile> multipartFile) {
        List<String> fileNameList = new ArrayList<>();

        multipartFile.forEach(file -> {
            String fileName = createFileName(file.getOriginalFilename());

            // Thumbnails 로직 추가 (230729)
            // @TODO : REFACTORING - 함수화 및 try-catch-resources 확인
            String thumbnailFileName = "thumbnails_" + fileName;
            File thumbnailFile = new File(thumbnailFileName);
            Thumbnails.Builder builder;
            try {
                BufferedImage originalImage = ImageIO.read(file.getInputStream());
                builder = Thumbnails.of(originalImage).size(100, 100);
                builder.toFile(thumbnailFile);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            ObjectMetadata objectMetadata = new ObjectMetadata();
            objectMetadata.setContentLength(file.getSize());
            objectMetadata.setContentType(file.getContentType());

            try(InputStream inputStream = file.getInputStream()) {
                amazonS3.putObject(new PutObjectRequest(bucket, "contents/" + fileName, inputStream, objectMetadata)
                        .withCannedAcl(CannedAccessControlList.PublicRead));
                amazonS3.putObject(bucket, "thumbnails/" + thumbnailFileName, thumbnailFile);
            } catch(IOException e) {
                throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "파일 업로드에 실패했습니다.");
            }

            fileNameList.add(fileName);
        });

        // DynamoDB 연동 로직 추가 (230729)
        // @TODO : REFACTORING - ContentController 기능을 가져다 씀.. 구조적 불합리성 개선 필요
        ContentDynamoRepository contentDynamoRepository = new ContentDynamoRepository();
        contentDynamoRepository.setDynamoDBMapper(this.dynamoDBMapper);
        fileNameList.forEach(url -> {
            ContentDynamo contentDynamo = new ContentDynamo();
            contentDynamo.setContentId("thumbnails_" + url);
            contentDynamo.setFileUrl(thumbnailUrl + url);
            contentDynamoRepository.saveContent(contentDynamo);

            contentDynamo.setContentId(url);
            contentDynamo.setFileUrl(contentUrl + url);
            contentDynamoRepository.saveContent(contentDynamo);
        });

        return fileNameList;
    }

    public void deleteFile(String fileName) {
        amazonS3.deleteObject(new DeleteObjectRequest(bucket, fileName));
        System.out.println("bucket = " + bucket);
    }

    private String createFileName(String fileName) {
        return UUID.randomUUID().toString().concat(getFileExtension(fileName));
    }

    private String getFileExtension(String fileName) { // file 형식이 잘못된 경우를 확인하기 위해 만들어진 로직이며, 파일 타입과 상관없이 업로드할 수 있게 하기 위해 .의 존재 유무만 판단하였습니다.
        try {
            return fileName.substring(fileName.lastIndexOf("."));
        } catch (StringIndexOutOfBoundsException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "잘못된 형식의 파일(" + fileName + ") 입니다.");
        }
    }
}