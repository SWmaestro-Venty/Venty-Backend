package com.swm.ventybackend.users;

import jakarta.annotation.Nullable;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UsersController {

    private final UsersService usersService;

    @PostMapping("/create")
    public String create(@RequestParam String email, String password, String users_name,
                         Integer gender, String nickname, Integer status, String profileImage) {
        // @TODO : profileImage File로 변경
        Users users = new Users();
        users.setEmail(email);
        users.setPassword(password);
        users.setUsersName(users_name);
        users.setGender(gender);
        users.setNickName(nickname);
        users.setStatus(status);
        users.setProfileImage(profileImage);
        Long usersId = usersService.saveUser(users);
        return usersId + "번 유저 등록 완료";
    }

    @DeleteMapping("/delete")
    public String delete(@RequestParam Long id) {
        usersService.removeUser(id);
        return id + "번 유저 삭제 완료";
    }

    @GetMapping("/findByIdOrName")
    public String read(@RequestParam @Nullable Long id, String name) {
        if(id != null) {
            return usersService.findUsersById(id).toString();
        } else {
            return usersService.findUsersByName(name).toString();
        }
    }

    @GetMapping("/all")
    public String readAll() {
        return usersService.findAllUsers().toString();
    }
}
