package com.swm.ventybackend.club_map;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class ClubMapService {

    private final ClubMapRepository clubMapRepository;

    public Long saveClubMap(ClubMap clubMap) {
        clubMapRepository.save(clubMap);
        return clubMap.getClubMapId();
    }

    public void removeClubMap(Long id) { clubMapRepository.remove(id); }

    public List<ClubMap> findClubMapByUsersId(Long usersId) {
        return clubMapRepository.findByUsersId(usersId);
    }

    public List<ClubMap> findClubMapByClubId(Long clubId) {
        return clubMapRepository.findByClubId(clubId);
    }

    public List<ClubMap> findAllClubMap() {
        return clubMapRepository.findAll();
    }

    public Long getClubMapCountByClubId(Long clubId) {
        return clubMapRepository.getClubMapCountByClubId(clubId);
    }

    public Boolean isUsersExistClubByUsersIdAndClubId(Long usersId, Long clubId) {
        return clubMapRepository.isUsersExistClubByUsersIdAndClubId(usersId, clubId);
    }
}
