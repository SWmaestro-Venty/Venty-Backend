package com.swm.ventybackend.usersTerm;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/usersTerm")
@RequiredArgsConstructor
public class UsersTermController {

    private final UsersTermService usersTermService;

    @PostMapping("/create")
    public String create(@RequestParam Long usersId, Boolean term1, Boolean term2) {
        UsersTerm usersTerm = new UsersTerm();
        usersTerm.setUsersId(usersId);
        usersTerm.setTerm1(term1);
        usersTerm.setTerm2(term2);

        Long usersTermId = usersTermService.saveUsersTerm(usersTerm);
        return usersTermId.toString();
    }

    @DeleteMapping("/delete")
    public String delete(@RequestParam Long id) {
        usersTermService.removeUsersTerm(id);
        return id.toString();
    }

    @GetMapping("/findUsersTermById")
    public UsersTerm findUsersTermById(Long id) {
        return usersTermService.findUsersTermById(id);
    }

    @GetMapping("/findUsersTermByUsersId")
    public List<UsersTerm> findUsersTermByUsersId(Long usersId) {
        return usersTermService.findUsersTermByUsersId(usersId);
    }

}
