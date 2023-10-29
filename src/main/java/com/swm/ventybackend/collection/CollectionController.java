package com.swm.ventybackend.collection;

import com.swm.ventybackend.club.Club;
import com.swm.ventybackend.club.ClubService;
import com.swm.ventybackend.content_rds.Content;
import com.swm.ventybackend.users.Users;
import com.swm.ventybackend.users.UsersService;
import jakarta.annotation.Nullable;
import jakarta.validation.constraints.Null;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.net.URLEncoder;
import java.util.List;

@RestController
@RequestMapping("/collection")
@RequiredArgsConstructor
public class CollectionController {

    private final CollectionService collectionService;


    @PostMapping("/create")
    public String create(@RequestParam String name, Long clubId, Long usersId, @Nullable Integer status, @Nullable String thumbnailImageUrl, @Nullable String collectionDescription) {

        Collection collection = new Collection();
        collection.setCollectionName(name);
        collection.setClubId(clubId);
        collection.setUsersId(usersId);

        if (thumbnailImageUrl != null)
            collection.setThumbnailImageUrl(thumbnailImageUrl);

        if (collectionDescription != null)
            collection.setCollectionDescription(collectionDescription);

        if (status != null)
            collection.setStatus(status);

        Long collectionId = collectionService.saveCollection(collection);

        return usersId +"번 유저 / " + clubId +"번 클럽 / " + collectionId + "번 컬렉션 등록 완료";
    }

    @DeleteMapping("/delete")
    public String delete(@RequestParam Long id) {
        collectionService.removeCollection(id);
        return id + "번 컬렉션 삭제 완료";
    }

    @GetMapping("/findByIdOrName")
    public Collection read(@RequestParam @Nullable Long id, @Nullable String name) {
        if(id != null) {
            return collectionService.findCollectionById(id);
        } else {
            return collectionService.findCollectionByName(name);
        }
    }

    @GetMapping("/findByUserId")
    public List<Collection> findCollectionByUserIdOrName(@RequestParam @Nullable Long usersId) {
        return collectionService.findCollectionByUsersId(usersId);
    }

    @GetMapping("/all")
    public String readAll() { return collectionService.findAllCollection().toString(); }

    @GetMapping("/setCollectionThumbnailByContentId")
    public String setCollectionThumbnailByContentId(@RequestParam Long collectionId, Long contentId) {
        collectionService.setCollectionThumbnailByContentId(collectionId, contentId);
        return collectionId + "번 컬렉션의 썸네일 변경 완료 : " + contentId + "번 콘텐츠의 썸네일";
    }

    @GetMapping("/findRandomCollectionByClubIdAndAmount")
    public List<Collection> findTenRandomCollectionByClubId(@RequestParam Long clubId, String amount) {
        return collectionService.findRandomCollectionByClubIdAndAmount(clubId, Integer.parseInt(amount));
    }

    @GetMapping("/findCollectionsByUsersIdAndClubId")
    public List<Collection> findCollectionsByUsersIdAndClubId(@RequestParam Long usersId, Long clubId) {
        return collectionService.findCollectionsByUsersIdAndClubId(usersId, clubId);
    }
}
