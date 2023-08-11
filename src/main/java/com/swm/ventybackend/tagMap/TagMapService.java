package com.swm.ventybackend.tagMap;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class TagMapService {

    private final TagMapRepository tagMapRepository;

    public Long saveTagMap(TagMap tagMap) {
        tagMapRepository.save(tagMap);
        return tagMap.getTagMapId();
    }

    public void removeTagMap(Long id) {
        tagMapRepository.remove(id);
    }

    public TagMap findTabMapById(Long id) {
        return tagMapRepository.findById(id);
    }

    public List<TagMap> findAllTagMap() {
        return tagMapRepository.findAll();
    }
}
