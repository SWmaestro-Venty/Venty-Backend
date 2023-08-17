package com.swm.ventybackend.content_map;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class ContentMapService {

    private final ContentMapRepository contentMapRepository;

    public Long saveContentMap(ContentMap contentMap) {
        contentMapRepository.save(contentMap);
        return contentMap.getContentMapId();
    }

    public void removeContentMap(Long id) {
        contentMapRepository.remove(id);
    }

    public ContentMap findContentMapById(Long id) {
        return contentMapRepository.findById(id);
    }

    public List<ContentMap> findAllContentMap() {
        return contentMapRepository.findAll();
    }

    public List<ContentMap> findContentIdByCollectionId(Long id) { return contentMapRepository.findAllContentIdByCollectionId(id); }
}
