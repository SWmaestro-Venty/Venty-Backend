package com.swm.ventybackend.tag;

import jakarta.annotation.Nullable;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tag")
@RequiredArgsConstructor
public class TagController {

    private final TagService tagService;

    @PostMapping("/create")
    public String create(@RequestParam String name, String type, @Nullable Integer status) {
        try {
            Tag searchTag = tagService.findTagByName(name);
            Long searchTagId = searchTag.getTagId();
            return name + " 태그는 " + searchTagId + " 번 태그로 이미 존재합니다.";
        } catch (Exception e) {
            Tag tag = new Tag();
            tag.setName(name);
            tag.setType(type);

            if (status != null)
                tag.setStatus(status);

            Long tagId = tagService.saveTag(tag);
            return tagId + "번 태그 생성 완료";
        }
    }

    @DeleteMapping("/delete")
    public String delete(@RequestParam Long id){
        tagService.removeTag(id);
        return id + "번 태그 삭제 완료";
    }

    @GetMapping("/findById")
    public String read(@RequestParam Long id) { return tagService.findTagById(id).toString(); }

    @GetMapping("/findByName")
    public Tag findByName(@RequestParam String name) { return tagService.findTagByName(name); }

    @GetMapping("/all")
    public List<Tag> findAll() { return tagService.findAllTag(); }
}
