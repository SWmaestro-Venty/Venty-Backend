package com.swm.ventybackend.feed;

import com.swm.ventybackend.collection.Collection;
import com.swm.ventybackend.feed.Feed;
import com.swm.ventybackend.feed.FeedRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class FeedService {
    
    private final FeedRepository feedRepository;

    public Long saveFeed(Feed feed) {
        feedRepository.save(feed);
        return feed.getFeedId();
    }

    public void removeFeed(Long id) { feedRepository.remove(id); }

    public Feed findFeedById(Long id) { return feedRepository.findById(id); }

    public Feed findFeedByTitle(String title) { return feedRepository.findByTitle(title); }

    public List<Feed> findAllFeed() { return feedRepository.findAll(); }

    public List<Feed> findFeedByCollectionId(Collection collection) {
        return feedRepository.findFeedByCollectionId(collection);
    }
}
