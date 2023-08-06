package com.swm.ventybackend.love;

import com.swm.ventybackend.users.Users;
import com.swm.ventybackend.users.UsersService;
import jakarta.annotation.Nullable;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/love")
@RequiredArgsConstructor
public class LoveController {

    private final LoveService loveService;
    private final UsersService usersService;

    @PostMapping("/create")
    public String create(@RequestParam @Nullable Long feedId, @Nullable Long collectionId,
                         @Nullable Long postId, @Nullable Long usersId, @Nullable Long commentId) {
        Love love = new Love();
//        if (feedId != null) love.setFeedId(feedId);
//        if (collectionId != null) love.setCollectionId(collectionId);
//        if (postId != null) love.setPostId(postId);
        if (usersId != null) {
            Users users = usersService.findUsersById(usersId);
            love.setUsers(users);
        }
        // commentId mapping

        Long loveId = loveService.saveLove(love);
        return loveId + "번 좋아요 등록 완료";
    }

    @DeleteMapping("/delete")
    public String delete(@RequestParam Long id){
        loveService.removeLove(id);
        return id + "번 좋아요 삭제 완료";
    }

    @GetMapping("/findById")
    public String read(@RequestParam Long id) {
        return loveService.findLoveById(id).toString();
    }

    @GetMapping("/all")
    public String readAll() { return loveService.findAllLove().toString(); }
}
