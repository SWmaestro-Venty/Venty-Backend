package com.swm.ventybackend.club;

import com.swm.ventybackend.content_rds.ContentRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class ClubService {

    private final ClubRepository clubRepository;
    private final ContentRepository contentRepository;

    public Long saveClub(Club club) {
        clubRepository.save(club);
        return club.getClubId();
    }

    public void removeClub(Long id) { clubRepository.remove(id); }

    public Club findClubById(Long id) { return clubRepository.findById(id); }

    public Club findClubByName(String name) { return clubRepository.findByName(name); }

    public List<Club> findAllClubs() { return clubRepository.findAllClubs(); }

    public Club setClubThumbnailByContentId(Long clubId, Long contentId) {
        String thumbnailUrl = contentRepository.findById(contentId).getThumbnailUrl();
        return clubRepository.updateThumbnail(clubId, thumbnailUrl);
    }

    public void updateClubThumbnailImageUrlByClubId(Long clubId, String thumbnailImageUrl) {
        clubRepository.updateClubThumbnailImageUrlByClubId(clubId, thumbnailImageUrl);
    }

}
