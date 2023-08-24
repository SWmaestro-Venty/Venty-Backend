package com.swm.ventybackend.content_rds;


import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import net.coobird.thumbnailator.Thumbnails;
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
import java.nio.Buffer;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class ContentService {

    private final ContentRepository contentRepository;

    @Value("${AWS_S3_BUCKET}")
    private String bucket;

    @Value("${AWS_S3_BUCKET_CONTENT_URL}")
    private String contentUrl;

    @Value("${AWS_S3_BUCKET_THUMBNAIL_URL}")
    private String thumbnailUrl;

    @Value("${AWS_CDN_DOMAIN}")
    private String cdnDomainUrl;

    private final AmazonS3 amazonS3;

    public Long saveContent(Content content) {
        contentRepository.save(content);
        return content.getContentId();
    }

    public void removeContent(Long id) { contentRepository.remove(id); }

    public Content findContentById(Long id) { return contentRepository.findById(id); }

    public List<Content> findContentsByUsersId(Long usersId) { return contentRepository.findContentsByUsersId(usersId); }

    public List<Content> findAllContent() { return contentRepository.findAll(); }

    public List<String> uploadFile(List<MultipartFile> multipartFiles) {
        List<String> fileNameList = new ArrayList<>();

        multipartFiles.forEach(file -> {
            String fileName = createFileName(file.getOriginalFilename());

            String thumbnailFileName = "thumbnails_" + fileName;
            File thumbnailFile = new File(thumbnailFileName);
            Thumbnails.Builder builder;
            try {
                BufferedImage originalImage = ImageIO.read(file.getInputStream());
                builder = Thumbnails.of(originalImage).size(400, 400);
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


//            23.08.24 S3 Direct -> CDN (CloudFront) 도입
//            fileNameList.add(contentUrl + fileName);
//            fileNameList.add(thumbnailUrl + fileName);

            // CDN
            fileNameList.add(cdnDomainUrl + "contents/" + fileName);
            fileNameList.add(cdnDomainUrl + "thumbnails/thumbnails_" + fileName);

        });
        return fileNameList;
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

    public List<Content> getTenContents() {
        return contentRepository.getTenContents();
    }
}