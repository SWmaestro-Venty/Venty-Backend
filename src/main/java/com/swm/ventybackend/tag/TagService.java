package com.swm.ventybackend.tag;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class TagService {

    private final TagRepository tagRepository;

    public Long saveTag(Tag tag) {
        tagRepository.save(tag);
        return tag.getTagId();
    }

    public void removeTag(Long id) {
        tagRepository.remove(id);
    }

    public Tag findTagById(Long id) {
        return tagRepository.findById(id);
    }

    public Tag findTagByName(String name) {
        return tagRepository.findByName(name);
    }

    public List<Tag> findAllTag() {
        return tagRepository.findAll();
    }
}
