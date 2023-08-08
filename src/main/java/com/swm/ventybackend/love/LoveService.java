package com.swm.ventybackend.love;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class LoveService {

    private final LoveRepository loveRepository;

    public Long saveLove(Love love) {
        loveRepository.save(love);
        return love.getLoveId();
    }

    public void removeLove(Long id) { loveRepository.remove(id); }

    public Love findLoveById(Long id) { return loveRepository.findById(id); }

    public Love findLoveByUserId(Long id) { return loveRepository.findByUsersId(id); }

    public List<Love> findAllLove() { return loveRepository.findAll(); }
}
