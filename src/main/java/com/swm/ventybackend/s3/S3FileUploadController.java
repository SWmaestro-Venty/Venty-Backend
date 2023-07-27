package com.swm.ventybackend.s3;

import io.swagger.v3.oas.models.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/s3")
public class S3FileUploadController {
    private final AwsS3Service awsS3Service;

    @PostMapping("/uploadFile")
    public ResponseEntity<List<String>> uploadFile(List<MultipartFile> multipartFiles) {
        return ResponseEntity.ok(awsS3Service.uploadFile(multipartFiles));
    }

    @DeleteMapping("/deleteFile")
    public ResponseEntity<String> deleteFile(@RequestParam String fileName) {
        awsS3Service.deleteFile(fileName);
        return ResponseEntity.ok(fileName);
    }
}