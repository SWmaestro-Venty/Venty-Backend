package com.swm.ventybackend.club;


import jakarta.annotation.Nullable;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

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
    public String read(@RequestParam @Nullable Long id, String name) {
        if(id != null) {
            return clubService.findClubById(id).toString();
        } else {
            return clubService.findClubByName(name).toString();
        }
    }

    @GetMapping("/all")
    public String readAll() {
        return clubService.findAllClub().toString();
    }
}
