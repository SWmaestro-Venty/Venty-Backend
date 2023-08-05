package com.swm.ventybackend.club;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class ClubService {

    private final ClubRepository clubRepository;

    public Long saveClub(Club club) {
        clubRepository.save(club);
        return club.getClubId();
    }

    public void removeClub(Long id) { clubRepository.remove(id); }

    public Club findClubById(Long id) { return clubRepository.findById(id); }

    public Club findClubByName(String name) { return clubRepository.findByName(name); }

    public List<Club> findAllClub() { return clubRepository.findAll(); }


}
