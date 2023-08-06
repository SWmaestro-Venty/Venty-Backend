package com.swm.ventybackend.feed;

import com.swm.ventybackend.board.Board;
import com.swm.ventybackend.board.BoardService;
import com.swm.ventybackend.collection.Collection;
import com.swm.ventybackend.collection.CollectionService;
import com.swm.ventybackend.feed.Feed;
import com.swm.ventybackend.feed.FeedService;
import jakarta.annotation.Nullable;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/feed")
@RequiredArgsConstructor
public class FeedController {

    private final FeedService feedService;
    private final CollectionService collectionService;

    @PostMapping("/create")
    public String create(@RequestParam String title, String context,
                         @Nullable Integer status, Long collectionId) {
        Feed feed = new Feed();
        feed.setFeedTitle(title);
        feed.setFeedContext(context);
        if (status != null) feed.setStatus(status);

        Collection collection = collectionService.findCollectionById(collectionId);
        feed.setCollection(collection);

        Long feedId = feedService.saveFeed(feed);
        return feedId + "번 피드 등록 완료";
    }

    @DeleteMapping("/delete")
    public String delete(@RequestParam Long id) {
        feedService.removeFeed(id);
        return id + "번 피드 삭제 완료";
    }

    @GetMapping("/findByIdOrTitle")
    public String read(@RequestParam @Nullable Long id, String title) {
        if(id != null) {
            return feedService.findFeedById(id).toString();
        } else {
            return feedService.findFeedByTitle(title).toString();
        }
    }

    @GetMapping("/all")
    public String readAll() { return feedService.findAllFeed().toString(); }
}

