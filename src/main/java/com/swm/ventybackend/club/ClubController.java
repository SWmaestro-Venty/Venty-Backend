package com.swm.ventybackend.club;


import com.swm.ventybackend.content_rds.ContentService;
import jakarta.annotation.Nullable;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/club")
@RequiredArgsConstructor
public class ClubController {

    private final ClubService clubService;
    private final ContentService contentService;

    @PostMapping("/create")
    public String create(@RequestParam Integer category, String name) {
        Club club = new Club();
        club.setCategory(category);
        club.setClubName(name);
        Long clubId = clubService.saveClub(club);
        return clubId + "번 그룹 등록 완료";
    }

    @DeleteMapping("/delete")
    public String delete(@RequestParam Long id) {
        clubService.removeClub(id);
        return id + "번 그룹 삭제 완료";
    }

    @GetMapping("/findByIdOrName")
    public Club read(@RequestParam @Nullable Long id, @Nullable String name) {
        if (id != null) {
            return clubService.findClubById(id);
        }

        else if (name != null) {
            return clubService.findClubByName(name);
        }

        return null;
    }

    @GetMapping("/findAllClubs")
    public List<Club> findAllClubs() {
        return clubService.findAllClubs();
    }

    @GetMapping("/setClubThumbnailByContentId")
    public String setClubThumbnailByContentId(@RequestParam Long clubId, Long contentId) {
        clubService.setClubThumbnailByContentId(clubId, contentId);
        return clubId + "번 클럽의 썸네일 변경 완료 : " + contentId + "번 콘텐츠의 썸네일";
    }

    @PostMapping("/updateClubThumbnailImageByClubId")
    public String updateClubThumbnailImageByClubId(@RequestParam Long clubId, MultipartFile file) {
        String existThumbnailImageUrl = clubService.findClubById(clubId).getThumbnailImageUrl();
        if (existThumbnailImageUrl != null) {
            contentService.deleteFileByFileUrl(existThumbnailImageUrl);
        }

        String thumbnailImageUrl = contentService.uploadThumbnailImage(file);
        clubService.updateClubThumbnailImageUrlByClubId(clubId, thumbnailImageUrl);
        return thumbnailImageUrl;
    }
}
