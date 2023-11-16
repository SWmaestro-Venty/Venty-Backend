package com.swm.ventybackend.users;

import com.swm.ventybackend.cloud.Cloud;
import com.swm.ventybackend.cloud.CloudService;
import com.swm.ventybackend.config.BaseException;
import com.swm.ventybackend.config.BaseResponse;
import com.swm.ventybackend.config.BaseResponseStatus;
import com.swm.ventybackend.content_rds.ContentService;
import com.swm.ventybackend.utils.JwtService;
import com.swm.ventybackend.utils.JwtSuccessDTO;
import io.swagger.v3.oas.annotations.Operation;
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

    @Operation(summary = "[0] 신규 유저 생성", description = "email 중복 검사 필요, password 검증 필요, status (1 : 일반 유저 / 2 : 컨트리뷰터 / 3 : 어드민)")
    @PostMapping("/create")
    public LoginUsersDTO create(@RequestParam String email, String password, @Nullable String gender, @Nullable String ageRange, @Nullable Integer status) {
        Users users = new Users();
        users.setEmail(email);
        users.setPassword(password);

        if (gender != null)
            users.setGender(gender);

        if (ageRange != null)
            users.setAgeRange(ageRange);

        if (status == null) status = 1;
        users.setStatus(status);

        Long usersId = usersService.saveUser(users);

        Cloud cloud = new Cloud();
        cloud.setUsersId(usersId);
        cloudService.saveCloud(cloud);

        LoginUsersDTO loginUsersDTO = new LoginUsersDTO();
        loginUsersDTO.setNewUsers(true);
        loginUsersDTO.setUsersId(usersId);
        loginUsersDTO.setJwtToken(jwtService.createAccessToken(usersId, status));
        return loginUsersDTO;
    }

    @Operation(summary = "[0] 카카오 (신규/기존) 유저 로그인")
    @PostMapping("/loginKakaoUser")
    public LoginUsersDTO loginKakaoUser(@RequestParam String kakaoId, @Nullable String email, @Nullable String gender, @Nullable String ageRange) {
        Optional<Users> users = usersService.findUsersByOAuthIdAndType(kakaoId, "kakao");

        if (users.isEmpty()) {
            Users newUsers = new Users();
            newUsers.setOAuthId(kakaoId);
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
            loginUsersDTO.setJwtToken(jwtService.createAccessToken(newUsersId, newUsers.getStatus()));
            return loginUsersDTO;
        }

        else if (users.isPresent()) {
            LoginUsersDTO loginUsersDTO = new LoginUsersDTO();
            loginUsersDTO.setNewUsers(false);
            loginUsersDTO.setUsersId(users.get().getUsersId());
            loginUsersDTO.setJwtToken(jwtService.createAccessToken(users.get().getUsersId(), users.get().getStatus()));
            return loginUsersDTO;
        }

        throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "카카오 유저 로그인 에러");
    }

    // @TODO : 구글, 애플 로그인 추가
    @Operation(summary = "[0] 구글 (신규/기존) 유저 로그인")
    @PostMapping("/loginGoogleUser")
    public LoginUsersDTO loginGoogleUser(@RequestParam String googleId, @Nullable String email, @Nullable String name) {
        Optional<Users> users = usersService.findUsersByOAuthIdAndType(googleId, "google");

        if (users.isEmpty()) {
            Users newUsers = new Users();
            newUsers.setOAuthId(googleId);
            newUsers.setOAuthType("google");

            if (name != null)
                newUsers.setUsersName(name);

            if (email != null)
                newUsers.setEmail(email);

            newUsers.setPassword(temporaryPassword);

            Long newUsersId = usersService.saveUser(newUsers);
            System.out.println("newUsersId = " + newUsersId);

            Cloud cloud = new Cloud();
            cloud.setUsersId(newUsersId);
            cloudService.saveCloud(cloud);


            LoginUsersDTO loginUsersDTO = new LoginUsersDTO();
            loginUsersDTO.setNewUsers(true);
            loginUsersDTO.setUsersId(newUsersId);
            loginUsersDTO.setJwtToken(jwtService.createAccessToken(newUsersId, newUsers.getStatus()));
            return loginUsersDTO;
        }

        else if (users.isPresent()) {
            LoginUsersDTO loginUsersDTO = new LoginUsersDTO();
            loginUsersDTO.setNewUsers(false);
            loginUsersDTO.setUsersId(users.get().getUsersId());
            loginUsersDTO.setJwtToken(jwtService.createAccessToken(users.get().getUsersId(), users.get().getStatus()));
            return loginUsersDTO;
        }

        throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "구글 유저 로그인 에러");
    }


    @Operation(summary = "[0] 애플 (신규/기존) 유저 로그인")
    @PostMapping("/loginAppleUser")
    public LoginUsersDTO loginAppleUser(@RequestParam String appleId, @Nullable String email, @Nullable String name, @Nullable String authCode, @Nullable String token) {
        Optional<Users> users = usersService.findUsersByOAuthIdAndType(appleId, "apple");

        if (users.isEmpty()) {
            Users newUsers = new Users();
            newUsers.setOAuthId(appleId);
            newUsers.setOAuthType("apple");

            if (email != null)
                newUsers.setEmail(email);

            if (name != null)
                newUsers.setUsersName(name);

            // @TODO : authCode, token ?

            newUsers.setPassword(temporaryPassword);

            Long newUsersId = usersService.saveUser(newUsers);
            System.out.println("newUsersId = " + newUsersId);

            Cloud cloud = new Cloud();
            cloud.setUsersId(newUsersId);
            cloudService.saveCloud(cloud);


            LoginUsersDTO loginUsersDTO = new LoginUsersDTO();
            loginUsersDTO.setNewUsers(true);
            loginUsersDTO.setUsersId(newUsersId);
            loginUsersDTO.setJwtToken(jwtService.createAccessToken(newUsersId, newUsers.getStatus()));
            return loginUsersDTO;
        }

        else if (users.isPresent()) {
            LoginUsersDTO loginUsersDTO = new LoginUsersDTO();
            loginUsersDTO.setNewUsers(false);
            loginUsersDTO.setUsersId(users.get().getUsersId());
            loginUsersDTO.setJwtToken(jwtService.createAccessToken(users.get().getUsersId(), users.get().getStatus()));
            return loginUsersDTO;
        }

        throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "애플 유저 로그인 에러");
    }

    // @TODO: OAuth 로그인 코드 정리

    @Operation(summary = "[0] Oauth 미사용 native 유저 로그인")
    @PostMapping("/nativeLogin")
    public LoginUsersDTO nativeLogin(String email, String password) {
        Optional<Users> users = usersService.findUsersByEmail(email);

        if (users.get().checkPassword(password, passwordEncoder)) {
            LoginUsersDTO loginUsersDTO = new LoginUsersDTO();
            loginUsersDTO.setNewUsers(false);
            loginUsersDTO.setUsersId(users.get().getUsersId());
            loginUsersDTO.setJwtToken(jwtService.createAccessToken(users.get().getUsersId(), users.get().getStatus()));
            return loginUsersDTO;
        }

        throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "로그인 실패");
    }

    @Operation(summary = "[1 - 3] 유저 삭제", description = "본인 계정 혹은 어드민에 의한 유저 삭제")
    @DeleteMapping("/delete")
    public BaseResponse<String> delete(@RequestParam Long usersId, @RequestHeader(value = "X-ACCESS-TOKEN") String accessToken) throws BaseException {
        try {
            JwtSuccessDTO jwtSuccessDTO = jwtService.getToken(accessToken);

            if (jwtSuccessDTO.getUsersId().equals(usersId.toString()) || jwtSuccessDTO.getStatus().equals("3")) {
                usersService.removeUser(usersId);
                return new BaseResponse<String>(usersId + "번 유저 삭제 완료");
            }

        } catch (BaseException baseException) {
            return new BaseResponse<>(baseException.getStatus());
        }
        throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "유저 삭제 에러");
    }


    @Operation(summary = "[3] UsersId로 Users 찾기")
    @GetMapping("/findUsersByUsersId")
    public Users findUsersByUsersId(@RequestParam Long usersId) {
        return usersService.findUsersById(usersId);
    }


    @Operation(summary = "[3] Email로 Users 찾기", description = "어드민에 의한 이메일 유저 조회")
    @GetMapping("/findUsersByEmail")
    public BaseResponse<Optional<Users>> findUsersByEmail(@RequestParam String email, @RequestHeader(value="X-ACCESS-TOKEN") String accessToken) throws BaseException{
        try {
            JwtSuccessDTO jwtSuccessDTO = jwtService.getToken(accessToken);

            if(jwtSuccessDTO.getStatus().equals("3")) {
                System.out.println("jwtSuccessDTO = " + jwtSuccessDTO.getUsersId());
                return new BaseResponse<Optional<Users>>(usersService.findUsersByEmail(email));
            }

            else {
                return new BaseResponse<>(BaseResponseStatus.INVALID_USER_JWT);
            }

        } catch (BaseException baseException) {
            return new BaseResponse<>(baseException.getStatus());
        }
    }

    @Operation(summary = "[0] 이메일 존재 여부 확인")
    @GetMapping("/emailExistCheck")
    public Boolean usersEmailExistCheck (String email) {
        if (usersService.findUsersByEmail(email).isPresent())
            return true;
        return false;
    }

    @Operation(summary = "[3] 전체 유저 목록 확인")
    @GetMapping("/all")
    public BaseResponse<Object> readAll(@RequestHeader(value = "X-ACCESS-TOKEN") @RequestParam String accessToken) throws BaseException {
        try {
            JwtSuccessDTO jwtSuccessDTO = jwtService.getToken(accessToken);

            if(jwtSuccessDTO.getStatus().equals("3"))
                return new BaseResponse<Object>(usersService.findAllUsers());

            return new BaseResponse<>(BaseResponseStatus.INVALID_USER_JWT);
        } catch (BaseException baseException) {
            return new BaseResponse<>(baseException.getStatus());
        }
    }


    @Operation(summary = "[0] 패스워드 테스트")
    @PostMapping("/passwordTestByEmail")
    public Object passwordTestByEmail(@RequestParam String email, String password) {
        Optional<Users> users = usersService.findUsersByEmail(email);

        if (users.isPresent()) {
            if (users.get().checkPassword(password, passwordEncoder)) {
                return true;
            }
            return false;
        }
        throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "비밀번호가 일치하지 않습니다.");
    }

    @Operation(summary = "[1 - 3] 유저/컨트리뷰터 닉네임 변경", description = "본인 혹은 어드민에 의한 닉네임 변경")
    @GetMapping("/updateUsersNicknameByUsersId")
    public BaseResponse<String> updateUsersNicknameByUsersId(@RequestParam Long usersId, String nickname, @RequestHeader(value = "X-ACCESS-TOKEN") String accessToken) throws BaseException {
        try {
            JwtSuccessDTO jwtSuccessDTO = jwtService.getToken(accessToken);

            if(jwtSuccessDTO.getUsersId().equals(usersId.toString()) || jwtSuccessDTO.getStatus().equals("3")) {
                usersService.updateUsersNicknameByUsersId(usersId, nickname);
                return new BaseResponse<String>(usersId + "번 유저 닉네임 변경 완료");
            }
        } catch (BaseException baseException) {
            return new BaseResponse<>(baseException.getStatus());
        }
        throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "유저 닉네임 업데이트 에러");
    }

    @Operation(summary = "[1 - 3] 유저/컨트리뷰터 프로필 이미지 경로 변경", description = "본인 혹은 어드민에 의한 프로필 이미지 변경")
    @GetMapping("/updateUsersProfileImageUrlByUsersId")
    public BaseResponse<String> updateUsersProfileImageUrlByUsersId(@RequestParam Long usersId, String profileImageUrl, @RequestHeader(value = "X-ACCESS-TOKEN") String accessToken) throws BaseException {
        try {
            JwtSuccessDTO jwtSuccessDTO = jwtService.getToken(accessToken);

            if(jwtSuccessDTO.getUsersId().equals(usersId.toString()) || jwtSuccessDTO.getStatus().equals("3")) {
                usersService.updateUsersProfileImageUrl(usersId, profileImageUrl);
                return new BaseResponse<String>(usersId + "번 유저 프로필 이미지 경로 변경 완료");
            }
        } catch (BaseException baseException) {
            return new BaseResponse<>(baseException.getStatus());
        }
        throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "유저 프로필 이미지 경로 업데이트 에러");
    }

    @Operation(summary = "[1 - 3] 유저/컨트리뷰터 프로필 이미지 변경", description = "본인 혹은 어드민에 의한 프로필 이미지 변경")
    @PostMapping("/updateUsersProfileImageByUsersId")
    public BaseResponse<String> updateUsersProfileImageByUsersId(@RequestParam Long usersId, MultipartFile file, @RequestHeader(value = "X-ACCESS-TOKEN") String accessToken) throws BaseException {
        try {
            JwtSuccessDTO jwtSuccessDTO = jwtService.getToken(accessToken);

            if(jwtSuccessDTO.getUsersId().equals(usersId.toString()) || jwtSuccessDTO.getStatus().equals("3")) {
                String existProfileImageUrl = usersService.findUsersById(usersId).getProfileImageUrl();
                if (existProfileImageUrl != null) {
                    contentService.deleteFileByFileUrl(existProfileImageUrl);
                }

                String profileImageUrl = contentService.uploadThumbnailImage(file);
                usersService.updateUsersProfileImageUrl(usersId, profileImageUrl);
                return new BaseResponse<String>(profileImageUrl);
            }
        } catch(BaseException baseException) {
            return new BaseResponse<>(baseException.getStatus());
        }
        throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "유저 프로필 이미지 업데이트 에러");
    }

    // @TODO : 그룹별 프로필
    // @TODO : 컨트리뷰터 제외
    // @TODO : 그룹에 들어갔을때, 그룹에 있던 기존 유저인지 판별하는 API -> 신규 유저면 그룹별 프로필 생성
    // @TODO : 아직 본인이 아닌 타인일 때 구분하는 로직이 없음. (231011)
    // @TODO : REFRESH TOKEN 관리법 추가해야함 (231011)

}
