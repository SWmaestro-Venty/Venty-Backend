package com.swm.ventybackend.collection;

import com.swm.ventybackend.club.Club;
import com.swm.ventybackend.club.ClubService;
import com.swm.ventybackend.users.Users;
import com.swm.ventybackend.users.UsersService;
import jakarta.annotation.Nullable;
import jakarta.validation.constraints.Null;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/collection")
@RequiredArgsConstructor
public class CollectionController {

    private final CollectionService collectionService;
    private final ClubService clubService;
    private final UsersService usersService;


    @PostMapping("/create")
    public String create(@RequestParam String name, Long clubId, Long usersId) {
        Collection collection = new Collection();
        collection.setCollectionName(name);

        Club club = clubService.findClubById(clubId);
        collection.setClub(club);

        Users users = usersService.findUsersById(usersId);
        collection.setUsers(users);

        Long collectionId = collectionService.saveCollection(collection);

        return usersId +"번 유저 / " + clubId +"번 클럽 / " + collectionId + "번 컬렉션 등록 완료";
    }

    @DeleteMapping("/delete")
    public String delete(@RequestParam Long id) {
        collectionService.removeCollection(id);
        return id + "번 컬렉션 삭제 완료";
    }

    @GetMapping("/findByIdOrName")
    public String read(@RequestParam @Nullable Long id, String name) {
        if(id != null) {
            return collectionService.findCollectionById(id).toString();
        } else {
            return collectionService.findCollectionByName(name).toString();
        }
    }

    @GetMapping("/findByUserId")
    public List<Collection> findCollectionByUserIdOrName(@RequestParam @Nullable Long usersId) {
        return collectionService.findCollectionByUsersId(usersId);
    }

    @GetMapping("/all")
    public String readAll() { return collectionService.findAllCollection().toString(); }
}
