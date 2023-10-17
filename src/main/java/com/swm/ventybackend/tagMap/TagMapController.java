package com.swm.ventybackend.tagMap;


import com.swm.ventybackend.content_rds.Content;
import com.swm.ventybackend.tag.TagService;
import jakarta.annotation.Nullable;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tagMap")
@RequiredArgsConstructor
public class TagMapController {

    private final TagMapService tagMapService;
    private final TagService tagService;

    @PostMapping("/create")
    public String create(@RequestParam @Nullable Long tagId, @Nullable String tagName, Long contentId) {
        // @TODO : contentId에 해당하는 content 존재 여부를 확인해야 함.
        TagMap tagMap = new TagMap();

        if (tagName != null) {
            Long nameSearchTagId = tagService.findTagByName(tagName).getTagId();
            tagMap.setTagId(nameSearchTagId);
            tagMap.setContentId(contentId);
        }

        if (tagId != null) {
            Long idSearchTagId = tagService.findTagById(tagId).getTagId();
            tagMap.setTagId(idSearchTagId);
            tagMap.setContentId(contentId);
        }

        Long tagMapId = tagMapService.saveTagMap(tagMap);
        return tagMapId + "번 태그 맵 생성 완료";
    }

    @DeleteMapping("/delete")
    public String delete(@RequestParam Long id) {
        tagMapService.removeTagMap(id);
        return id + "번 태그 맵 삭제 완료";
    }

    @GetMapping("/findById")
    public String read(@RequestParam Long id) {
        return tagMapService.findTabMapById(id).toString();
    }

    @GetMapping("/all")
    public List<TagMap> readAll() {
        return tagMapService.findAllTagMap();
    }

    @GetMapping("/findTagMapsByContentId")
    public List<TagMap> findTagMapsByContentId(@RequestParam Long contentId) {
        return tagMapService.findTagMapsByContentId(contentId);
    }

//    @GetMapping("/findContentByManyTagId")
//    public List<Content> findContentByManyTagName(@RequestParam List<String> tagNameList) {
//        return tagMapService.findContentByManyTagName(tagNameList);;
//    }
}
