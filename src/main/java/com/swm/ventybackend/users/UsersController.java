package com.swm.ventybackend.users;

import com.swm.ventybackend.cloud.Cloud;
import com.swm.ventybackend.cloud.CloudService;
import com.swm.ventybackend.config.BaseException;
import com.swm.ventybackend.content_rds.ContentService;
import com.swm.ventybackend.utils.JwtService;
import jakarta.annotation.Nullable;
import jakarta.validation.constraints.Null;
import lombok.RequiredArgsConstructor;
import org.apache.http.protocol.HTTP;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import javax.swing.text.html.Option;
import java.util.Optional;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UsersController {

    private final UsersService usersService;
    private final CloudService cloudService;
    private final ContentService contentService;

    private final JwtService jwtService;

    @Value("${TEMPORARY_PASSWORD}")
    private String temporaryPassword;

    @Autowired
    private PasswordEncoder passwordEncoder;


    @PostMapping("/create")
    public LoginUsersDTO create(@RequestParam String email, String password, @Nullable String gender, @Nullable String ageRange, @Nullable Integer status) {
        Users users = new Users();
        users.setEmail(email);
        users.setPassword(password);

        if (gender != null)
            users.setGender(gender);

        if (ageRange != null)
            users.setAgeRange(ageRange);

        if (status != null)
            users.setStatus(status);

        Long usersId = usersService.saveUser(users);

        Cloud cloud = new Cloud();
        cloud.setUsersId(usersId);
        cloudService.saveCloud(cloud);

        LoginUsersDTO loginUsersDTO = new LoginUsersDTO();
        loginUsersDTO.setNewUsers(true);
        loginUsersDTO.setUsersId(usersId);
        loginUsersDTO.setJwtToken(jwtService.createJwt(usersId));
        return loginUsersDTO;
    }

    @PostMapping("/loginKakaoUser")
    public LoginUsersDTO loginKakaoUser(@RequestParam String kakaoId, @Nullable String email, @Nullable String gender, @Nullable String ageRange) {
        Optional<Users> users = usersService.findUsersByOAuthIdAndType(Long.valueOf(kakaoId), "kakao");

        if (users.isEmpty()) {
            Users newUsers = new Users();
            newUsers.setOAuthId(Long.valueOf(kakaoId));
            newUsers.setOAuthType("kakao");

            if (ageRange != null)
                newUsers.setAgeRange(ageRange);

            if (email != null)
                newUsers.setEmail(email);

            if (gender != null)
                newUsers.setGender(gender);

            newUsers.setPassword(temporaryPassword);

            Long newUsersId = usersService.saveUser(newUsers);
            System.out.println("newUsersId = " + newUsersId);

            Cloud cloud = new Cloud();
            cloud.setUsersId(newUsersId);
            cloudService.saveCloud(cloud);


            LoginUsersDTO loginUsersDTO = new LoginUsersDTO();
            loginUsersDTO.setNewUsers(true);
            loginUsersDTO.setUsersId(newUsersId);

            loginUsersDTO.setJwtToken(jwtService.createJwt(newUsersId));
            return loginUsersDTO;
        }

        else if (users.isPresent()) {
            LoginUsersDTO loginUsersDTO = new LoginUsersDTO();
            loginUsersDTO.setNewUsers(false);
            loginUsersDTO.setUsersId(users.get().getUsersId());
            loginUsersDTO.setJwtToken(jwtService.createJwt(users.get().getUsersId()));
            return loginUsersDTO;
        }

        throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "카카오 유저 로그인 에러");
    }

    // @TODO : 구글, 애플 로그인

    @PostMapping("/nativeLogin")
    public LoginUsersDTO nativeLogin(String email, String password) {
        Optional<Users> users = usersService.findUsersByEmail(email);

        if (users.get().checkPassword(password, passwordEncoder)) {
            LoginUsersDTO loginUsersDTO = new LoginUsersDTO();
            loginUsersDTO.setNewUsers(false);
            loginUsersDTO.setUsersId(users.get().getUsersId());
            loginUsersDTO.setJwtToken(jwtService.createJwt(users.get().getUsersId()));
            return loginUsersDTO;
        }

        throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "로그인 실패");
    }

    @DeleteMapping("/delete")
    public String delete(@RequestParam Long id) {
        usersService.removeUser(id);
        return id + "번 유저 삭제 완료";
    }

    @GetMapping("/findUsersByUsersId")
    public Users findUsersByUsersId(@RequestParam Long usersId) {
        return usersService.findUsersById(usersId);
    }

    @GetMapping("/findByEmail")
    public Optional<Users> findByEmail(@RequestParam String email) {
        return usersService.findUsersByEmail(email);
    }

    @GetMapping("/emailExistCheck")
    public Boolean usersEmailExistCheck (String email) {
        if (usersService.findUsersByEmail(email).isPresent())
            return true;
        return false;
    }

    @GetMapping("/all")
    public Object readAll() {
        return usersService.findAllUsers();
    }


    @PostMapping("/passwordTest")
    public Object passwordTest(@RequestParam String email, String password) {
        Optional<Users> users = usersService.findUsersByEmail(email);

        if (users.isPresent()) {
            if (users.get().checkPassword(password, passwordEncoder)) {
                return true;
            }
            return false;
        }
        throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "비밀번호가 일치하지 않습니다.");
    }

    @GetMapping("/updateUsersNicknameByUsersId")
    public void updateUsersNicknameByUsersId(@RequestParam Long usersId, String nickname) {
        usersService.updateUsersNicknameByUsersId(usersId, nickname);
    }

    @GetMapping("/updateUsersProfileImageUrlByUsersId")
    public void updateUsersProfileImageUrlByUsersId(@RequestParam Long usersId, String profileImageUrl) {
        usersService.updateUsersProfileImageUrl(usersId, profileImageUrl);
    }

    @PostMapping("/updateUsersProfileImageByUsersId")
    public String updateUsersProfileImageByUsersId(@RequestParam Long usersId, MultipartFile file) {
        String existProfileImageUrl = usersService.findUsersById(usersId).getProfileImageUrl();
        if (existProfileImageUrl != null) {
            contentService.deleteFileByFileUrl(existProfileImageUrl);
        }

        String profileImageUrl = contentService.uploadThumbnailImage(file);
        usersService.updateUsersProfileImageUrl(usersId, profileImageUrl);
        return profileImageUrl;
    }

    // @TODO : 그룹별 프로필
    // @TODO : 컨트리뷰터 제외
    // @TODO : 그룹에 들어갔을때, 그룹에 있던 기존 유저인지 판별하는 API -> 신규 유저면 그룹별 프로필 생성

}
