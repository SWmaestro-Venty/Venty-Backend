package com.swm.ventybackend.clubBasicTagMap;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/clubBasicTagMap")
@RequiredArgsConstructor
public class ClubBasicTagMapController {

    private final ClubBasicTagMapService clubBasicTagMapService;

    @PostMapping("/create")
    public String create(@RequestParam Long clubId, Long tagId) {
        ClubBasicTagMap clubBasicTagMap = new ClubBasicTagMap();
        clubBasicTagMap.setClubId(clubId);
        clubBasicTagMap.setTagId(tagId);

        Long clubBasicTagMapId = clubBasicTagMapService.saveClubBasicTagMap(clubBasicTagMap);
        return clubBasicTagMapId.toString();
    }

    @DeleteMapping("/delete")
    public String delete(@RequestParam Long id) {
        clubBasicTagMapService.removeClubBasicTagMap(id);
        return id + "번 클럽 기본 태그 맵 삭제";
    }

    @GetMapping("/findClubBasicTagMapById")
    public ClubBasicTagMap findClubBasicTagMapById(@RequestParam Long id) {
        return clubBasicTagMapService.findClubBasicTagMapById(id);
    }

    @GetMapping("/findClubBasicTagMapsByClubId")
    public List<ClubBasicTagMap> findClubBasicTagMapsByClubId(@RequestParam Long clubId) {
        return clubBasicTagMapService.findClubBasicTagMapsByClubId(clubId);
    }
}
