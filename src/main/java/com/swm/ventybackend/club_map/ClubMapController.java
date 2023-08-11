package com.swm.ventybackend.club_map;

import com.swm.ventybackend.club.Club;
import com.swm.ventybackend.collection.Collection;
import com.swm.ventybackend.collection.CollectionService;
import jakarta.annotation.Nullable;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/clubMap")
@RequiredArgsConstructor
public class ClubMapController {

    private final ClubMapService clubMapService;
    private final CollectionService collectionService;

    @PostMapping("/create")
    public String create(@RequestParam Long usersId, Long clubId) {
        ClubMap clubMap = new ClubMap();
        clubMap.setClubId(clubId);
        clubMap.setUsersId(usersId);
        clubMapService.saveClubMap(clubMap);

        Collection collection = new Collection();
        collection.setCollectionName("기본 컬렉션");
        collection.setClubId(clubId);
        collection.setUsersId(usersId);
        Long collectionId = collectionService.saveCollection(collection);

        return clubId + "번 클럽, " + usersId + "번 유저 맵핑, " + collectionId + "번 기본 컬렉션 생성 완료";
    }

    @DeleteMapping("/delete")
    public String delete(@RequestParam Long id) {
        clubMapService.removeClubMap(id);
        return id + "번 클럽 매핑 삭제 완료";
    }

    @GetMapping("/findByUsersId")
    public List<ClubMap> findClubMapByUsersId(@RequestParam Long usersId) {
        return clubMapService.findClubMapByUsersId(usersId);
    }

    @GetMapping("/findByClubId")
    public List<ClubMap> findClubMapByClubId(@RequestParam Long clubId) {
        return clubMapService.findClubMapByClubId(clubId);
    }

    @GetMapping("/all")
    public List<ClubMap> findClubMapAll() {
        return clubMapService.findAllClubMap();
    }
}
