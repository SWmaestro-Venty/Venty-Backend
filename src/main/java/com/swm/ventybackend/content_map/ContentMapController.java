package com.swm.ventybackend.content_map;

import com.swm.ventybackend.content_rds.Content;
import jakarta.annotation.Nullable;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/contentMap")
@RequiredArgsConstructor
public class ContentMapController {

    private final ContentMapService contentMapService;

    @PostMapping("/create")
    public String create(@RequestParam Long contentId, @Nullable Long collectionId, @Nullable Long feedId) {
        if (collectionId == null && feedId == null)
            return new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Collection, Feed Both Data is Null").getMessage();

        ContentMap contentMap = new ContentMap();
        contentMap.setContentId(contentId);

        if (collectionId != null)
            contentMap.setCollectionId(collectionId);
        else
            contentMap.setFeedId(feedId);

        Long contentMapId = contentMapService.saveContentMap(contentMap);
        return contentMapId.toString();
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

    @GetMapping("/findContentIdByCollectionId")
    public List<ContentMap> findContentIdByCollectionId(@RequestParam Long collectionId) {
        return contentMapService.findContentIdByCollectionId(collectionId);
    }

}
