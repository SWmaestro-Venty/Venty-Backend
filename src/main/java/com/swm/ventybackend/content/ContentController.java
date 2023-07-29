package com.swm.ventybackend.content;

import com.swm.ventybackend.dynamoDb.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ContentController {

    @Autowired
    private ContentRepository contentRepository;

    @PostMapping("/add/content")
    public Content saveContent(@RequestBody Content content) {
        return contentRepository.saveContent(content);
    }

    @GetMapping("/get/content/{id}")
    public Content getContentById(@PathVariable("id") String contentId) {
        return contentRepository.getContentById(contentId);
    }

    @DeleteMapping("/delete/content/{id}")
    public String deleteContentById(@PathVariable("id") String contentId) {
        return contentRepository.deleteContentById(contentId);
    }

    @PutMapping("/update/content/{id}")
    public String updateContent(@PathVariable("id") String contentId, @RequestBody Content content) {
        return contentRepository.updateContent(contentId, content);
    }

    @GetMapping("/get/content/new")
    public List<Content> getContentNew() {
        return contentRepository.getContent();
    }
}
