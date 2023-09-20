package com.swm.ventybackend.users;

import com.swm.ventybackend.cloud.Cloud;
import com.swm.ventybackend.cloud.CloudService;
import jakarta.annotation.Nullable;
import jakarta.validation.constraints.Null;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.swing.text.html.Option;
import java.util.Optional;

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
                         String gender, String nickname, Integer status) {
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

        // @TODO: 디비 변동에 맞게 수정
    }

    @PostMapping("/loginKakaoUser")
    public String loginKakaoUser(@RequestParam String kakaoId, String email, String gender, String ageRange) {
        // 3001771464, junghk0115@naver.com, Gender.female, AgeRange.age_20_29

        Optional<Users> users = usersService.findUsersByOAuthIdAndType(Long.valueOf(kakaoId), "kakao");

        if (users.isEmpty()) {
            Users newUsers = new Users();
            newUsers.setOAuthId(Long.valueOf(kakaoId));
            newUsers.setOAuthType("kakao");
            newUsers.setAgeRange(ageRange);
            newUsers.setEmail(email);
            newUsers.setGender(gender);
            newUsers.setPassword("VentyKakaoTemporaryPassword-!@#!@#");

            Long newUsersId = usersService.saveUser(newUsers);
            System.out.println("newUsersId = " + newUsersId);

            Cloud cloud = new Cloud();
            cloud.setUsersId(newUsersId);
            Long cloudId = cloudService.saveCloud(cloud);

            return newUsersId + "번 Kakao 유저 " + cloudId + "번 클라우드 신규 등록 완료";

        }

        else if (users.isPresent()) {
            return users.get().getUsersId() + "번 userId, " + users.get().getOAuthId() + "번 OauthId 로 KAKAO 유저 로그인";
        }

        // @TODO : JWT 토큰 발급
        return null;
    }

    // @TODO : 구글, 애플 로그인
    // @TODO : 프로필 사진 및 닉네임 정하는 API

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
    public Optional<Users> findByEmail(@RequestParam String email) {
        return usersService.findUsersByEmail(email);
    }

    @GetMapping("/all")
    public Object readAll() {
        return usersService.findAllUsers();
    }


    @GetMapping("/passwordTest")
    public Object passwordTest(@RequestParam String email, String password) {
        Optional<Users> users = usersService.findUsersByEmail(email);

        if (users.isPresent()) {
            if (users.get().checkPassword(password, passwordEncoder)) {
                return "패스워드가 동일합니다.";
            }
            return "패스워드가 다릅니다.";
        }
        return "이메일에 해당하는 유저가 존재하지 않습니다.";

    }

//    @GetMapping("/kakao")
//    public String kakaoCallback(@RequestParam String code) {
//        String response = "카카오 로그인 API 코드를 불러오는데에 성공하였습니다. " + code;
//        return response;
//    }

    @GetMapping("/updateUsersInfoByUsersId")
    public String updateUsersInfoByUsersId(@RequestParam Long usersId, String nickname, String profileImageUrl) {
        usersService.updateUsersInfoByUsersId(usersId, nickname, profileImageUrl);
        return usersId + "번 유저의 정보를 변경합니다.";
    }
}
