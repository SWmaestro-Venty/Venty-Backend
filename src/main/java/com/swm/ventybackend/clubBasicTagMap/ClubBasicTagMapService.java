package com.swm.ventybackend.clubBasicTagMap;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class ClubBasicTagMapService {

    private final ClubBasicTagMapRepository clubBasicTagMapRepository;

    public Long saveClubBasicTagMap(ClubBasicTagMap clubBasicTagMap) {
        clubBasicTagMapRepository.save(clubBasicTagMap);
        return clubBasicTagMap.getClubBasicTagMapId();
    }

    public void removeClubBasicTagMap(Long id) {
        clubBasicTagMapRepository.remove(id);
    }

    public ClubBasicTagMap findClubBasicTagMapById(Long id) {
        return clubBasicTagMapRepository.findById(id);
    }

    public List<ClubBasicTagMap> findClubBasicTagMapsByClubId(Long clubId) {
        return clubBasicTagMapRepository.findClubBasicTagMapsByClubId(clubId);
    }
}
