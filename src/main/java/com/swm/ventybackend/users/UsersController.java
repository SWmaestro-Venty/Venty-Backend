package com.swm.ventybackend.users;

import com.swm.ventybackend.cloud.Cloud;
import com.swm.ventybackend.cloud.CloudService;
import jakarta.annotation.Nullable;
import jakarta.validation.constraints.Null;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UsersController {

    private final UsersService usersService;
    private final CloudService cloudService;

    @Autowired
    private PasswordEncoder passwordEncoder;

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

        Cloud cloud = new Cloud();
        cloud.setUsersId(usersId);
        Long cloudId = cloudService.saveCloud(cloud);

        return usersId + "번 유저 " + cloudId + "번 클라우드 등록 완료";
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

    // @TODO : email로 password만 받아오기 / Users 객체 말고
    @GetMapping("/passwordTest")
    public Object passwordTest(@RequestParam String email, String password) {
        Users users = usersService.findUsersByEmail(email);
        if(users.checkPassword(password, passwordEncoder)) {
            return "패스워드가 동일합니다.";
        }
        return "패스워드가 다릅니다";
    }

    @GetMapping("/kakao")
    public String kakaoCallback(@RequestParam String code) {
        String response = "카카오 로그인 API 코드를 불러오는데에 성공하였습니다. " + code;
        return response;
    }
}
