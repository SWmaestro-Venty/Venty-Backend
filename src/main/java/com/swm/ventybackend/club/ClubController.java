package com.swm.ventybackend.club;


import jakarta.annotation.Nullable;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/club")
@RequiredArgsConstructor
public class ClubController {

    private final ClubService clubService;

    @PostMapping("/create")
    public String create(@RequestParam Integer category, String name, @Nullable String thumbnailImageUrl) {
        // @TODO thumbnailImageUrl File로 변경
        Club club = new Club();
        club.setCategory(category);
        club.setClubName(name);
        if (thumbnailImageUrl != null)
            club.setThumbnailImageUrl(thumbnailImageUrl);
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

    @GetMapping("/all")
    public String readAll() {
        return clubService.findAllClub().toString();
    }

    @GetMapping("/setClubThumbnailByContentId")
    public String setClubThumbnailByContentId(@RequestParam Long clubId, Long contentId) {
        clubService.setClubThumbnailByContentId(clubId, contentId);
        return clubId + "번 클럽의 썸네일 변경 완료 : " + contentId + "번 콘텐츠의 썸네일";
    }
}
