package com.swm.ventybackend.content_dynamo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ContentDynamoController {

    @Autowired
    private ContentDynamoRepository contentDynamoRepository;

    @PostMapping("/add/content")
    public ContentDynamo saveContent(@RequestBody ContentDynamo contentDynamo) {
        return contentDynamoRepository.saveContent(contentDynamo);
    }

    @GetMapping("/get/content/{id}")
    public ContentDynamo getContentById(@PathVariable("id") String contentId) {
        return contentDynamoRepository.getContentById(contentId);
    }

    @DeleteMapping("/delete/content/{id}")
    public String deleteContentById(@PathVariable("id") String contentId) {
        return contentDynamoRepository.deleteContentById(contentId);
    }

    @PutMapping("/update/content/{id}")
    public String updateContent(@PathVariable("id") String contentId, @RequestBody ContentDynamo contentDynamo) {
        return contentDynamoRepository.updateContent(contentId, contentDynamo);
    }

    @GetMapping("/get/content/new")
    public List<ContentDynamo> getContentNew() {
        return contentDynamoRepository.getContent();
    }
}
