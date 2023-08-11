package com.swm.ventybackend.content_rds;


import jakarta.annotation.Nullable;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/content")
@RequiredArgsConstructor
public class ContentController {

    private final ContentService contentService;


    @PostMapping("/create")
    public String create(@RequestParam @Nullable Integer status, Integer isImageOrVideo, String extension, String size,
                         Long usersId, List<MultipartFile> multipartFiles) {

        // @TODO : isImageOrVideo, extension, size 자동화
        // @TODO : 생성된 썸네일 파일 삭제
        List<String> fileNameList = contentService.uploadFile(multipartFiles);
        String returnMessage = "";

        for(int i = 0; i < fileNameList.size() / 2 ; i+=2) {
            Content content = new Content();
            content.setOriginalUrl(fileNameList.get(i));
            content.setThumbnailUrl(fileNameList.get(i) + 1);

            // @TODO : 아래 3개 관리해줘야함
            content.setIsImageOrVideo(isImageOrVideo);
            content.setExtension(extension);
            content.setSize(size);
            content.setUsersId(usersId);

            if (status != null) content.setStatus(status);

            Long contentId = contentService.saveContent(content);
            returnMessage += (contentId + "번 ");
        }

        return returnMessage + "콘텐츠 등록 완료";
    }

    @DeleteMapping("/delete")
    public String delete(@RequestParam Long id) {
        contentService.removeContent(id);
        return id + "번 콘텐츠 삭제 완료";
    }

    @GetMapping("/findById")
    public String read(@RequestParam Long id) {
        return contentService.findContentById(id).toString();
    }

    @GetMapping("/findContentsByUsersId")
    public List<Content> findContentsByUsersId(@RequestParam Long usersId) { return contentService.findContentsByUsersId(usersId); }

    @GetMapping("/all")
    public String readAll() { return contentService.findAllContent().toString(); }

    @GetMapping("/getTenRandomContents")
    public List<Content> getTenRandomContents() {
        return contentService.getTenContents();
    }
}

