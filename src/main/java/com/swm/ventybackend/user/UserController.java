package com.swm.ventybackend.user;

import jakarta.annotation.Nullable;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/join")
    public String create(@RequestParam String email, String password, String user_name,
                         Integer gender, String nickname, Integer status) {
        User user = new User();
        user.setEmail(email);
        user.setPassword(password);
        user.setUserName(user_name);
        user.setGender(gender);
        user.setNickName(nickname);
        user.setStatus(status);

        Long userId = userService.saveUser(user);
        return userId + "번 유저 등록 완료";
    }

    @DeleteMapping("/delete")
    public String delete(@RequestParam Long id) {
        userService.removeUser(id);
        return id + "번 유저 삭제 완료";
    }

    @GetMapping("/findByIdOrName")
    public String read(@RequestParam @Nullable Long id, String name) {
        if(id != null) {
            return userService.findUserById(id).toString();
        } else {
            return userService.findUserByName(name).toString();
        }
    }

    @GetMapping("/all")
    public String readAll() {
        return userService.findAllUser().toString();
    }
}
