package com.swm.ventybackend.club_map;

import com.swm.ventybackend.club.Club;
import com.swm.ventybackend.club.ClubService;
import com.swm.ventybackend.collection.Collection;
import com.swm.ventybackend.collection.CollectionService;
import com.swm.ventybackend.privateClubDetail.PrivateClubDetail;
import com.swm.ventybackend.privateClubDetail.PrivateClubDetailService;
import jakarta.annotation.Nullable;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/clubMap")
@RequiredArgsConstructor
public class ClubMapController {

    private final ClubMapService clubMapService;
    private final CollectionService collectionService;
    private final PrivateClubDetailService privateClubDetailService;
    private final ClubService clubService;

    @PostMapping("/create")
    public String create(@RequestParam Long usersId, Long clubId) {

        // 이미 가입된 클럽인 경우
        if (clubMapService.isUsersExistClubByUsersIdAndClubId(usersId, clubId)) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "이미 가입된 클럽");
        }

        // 프라이빗 클럽의 가입 정원을 초과한 경우
        Optional<PrivateClubDetail> pcd = privateClubDetailService.findPrivateClubDetailByClubId(clubId);
        if (pcd.isPresent() && (clubService.getCurrentClubUsersCountByClubId(clubId) >= pcd.get().getPrivateClubMaxUsers())) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "클럽 가입 정원을 초과하였습니다.");
        }

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

    @GetMapping("/getClubMapCountByClubId")
    public Long getClubMapCountByClubId(Long clubId) {
        return clubMapService.getClubMapCountByClubId(clubId);
    }
}
