package com.swm.ventybackend.content_map;

import jakarta.annotation.Nullable;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/contentMap")
@RequiredArgsConstructor
public class ContentMapController {

    private final ContentMapService contentMapService;

    @PostMapping("/create")
    public String create(@RequestParam Long contentId, @Nullable Long collectionId, @Nullable Long feedId) {
        ContentMap contentMap = new ContentMap();
        contentMap.setContentId(contentId);

        if (collectionId != null)
            contentMap.setCollectionId(collectionId);

        if (feedId != null)
            contentMap.setFeedId(feedId);

        Long contentMapId = contentMapService.saveContentMap(contentMap);

        return contentMapId + "번 맵 생성 완료";
    }

    @DeleteMapping("/delete")
    public String delete(@RequestParam Long id) {
        contentMapService.removeContentMap(id);
        return id + "번 콘텐츠 맵 삭제 완료";
    }

    @GetMapping("/findById")
    public String read(@RequestParam Long id) {
        return contentMapService.findContentMapById(id).toString();
    }

    @GetMapping("/all")
    public List<ContentMap> readAll() {
        return contentMapService.findAllContentMap();
    }

}
