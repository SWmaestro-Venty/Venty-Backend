package com.swm.ventybackend.users;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.Null;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UsersController {

    private final UsersService usersService;

    @PostMapping("/create")
    public String create(@RequestParam String email, String password, String users_name,
                         Integer gender, String nickname, Integer status) {
        // @TODO : profileImage File로 변경 및 API 분리
        Users users = new Users();
        users.setEmail(email);
        users.setPassword(password);
        users.setUsersName(users_name);
        users.setGender(gender);
        users.setNickName(nickname);
        users.setStatus(status);
        Long usersId = usersService.saveUser(users);
        return usersId + "번 유저 등록 완료";
    }

    @DeleteMapping("/delete")
    public String delete(@RequestParam Long id) {
        usersService.removeUser(id);
        return id + "번 유저 삭제 완료";
    }

    @GetMapping("/findByIdOrName")
    public String read(@RequestParam Long id) {
        return usersService.findUsersById(id).toString();
    }

    @GetMapping("/findByName")
    public String findByName(@RequestParam String name) {
        return usersService.findUsersByName(name).toString();
    }

    @GetMapping("/findByEmail")
    public Object findByEmail(@RequestParam String email) {
        return usersService.findUsersByEmail(email);
    }

    @GetMapping("/all")
    public Object readAll() {
        return usersService.findAllUsers();
    }
}
