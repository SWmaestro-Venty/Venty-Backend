package com.swm.ventybackend.s3;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RequiredArgsConstructor
@Controller
public class TestSingleWebController {
    // @TODO : 테스트용 싱글 업로드 컨트롤러

    private final TestSingleS3Uploader testSingleS3Uploader;

    @GetMapping("/index")
    public String index() {
        return "index";
    }

    @PostMapping("/upload")
    @ResponseBody
    public String upload(@RequestParam("data")MultipartFile multipartFile) throws IOException {
        return testSingleS3Uploader.upload(multipartFile, "test");
    }
}
