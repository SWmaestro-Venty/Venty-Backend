package com.swm.ventybackend.config.auth;

import com.swm.ventybackend.config.BaseException;
import com.swm.ventybackend.config.BaseResponseStatus;
import com.swm.ventybackend.config.auth.kakao.KakaoOAuth;
import com.swm.ventybackend.config.auth.kakao.KakaoUser;
import com.swm.ventybackend.users.GetUserRes;
import com.swm.ventybackend.users.PostLogInRes;
import com.swm.ventybackend.users.Users;
import com.swm.ventybackend.users.UsersService;
import com.swm.ventybackend.utils.JwtService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
@Transactional
public class OAuthService {
    private final KakaoOAuth kakaoOAuth;
    private final UsersService usersService;
    private final JwtService jwtService;

    public PostLogInRes kakaoLogin(String code) throws BaseException {
        KakaoUser kakaoUser = kakaoOAuth.getKakaoUser(code);
        Long userId = kakaoUser.getUserId();
        String email = kakaoUser.getEmail();
        String userName = kakaoUser.getName();

        // ID로 조회

        Users users = usersService.findUsersById(userId);
        if (users == null) {

            // Email로 조회
            Optional<Users> users2 = usersService.findUsersByEmail(email);

            if (users2.isEmpty()) {
                Users users3 = new Users();
//                users3.setUsersId(userId);
                users3.setNickName(userId.toString()); // 에러 때문에 일단 nickname에 저장
                users3.setEmail(email);
                users3.setUsersName(userName);
                users3.setPassword("dlatlfhsjgdjensmsvotmdnjem");
                usersService.saveUser(users3);
            }

            try {
                Long kakaoId = usersService.findUsersById(userId).getUsersId();
                String jwt = jwtService.createJwt(userId);
                return new PostLogInRes(kakaoId, jwt);
            } catch (Exception ingored) {
                throw new BaseException(BaseResponseStatus.PASSWORD_ENCRYPTION_ERROR);
            }
        }
        /*
        1. userId로 유저 조회
        2. 없으면 userEmail로 유저 조회
        3. 없으면 user 생성, kakaoId 생성
        4. 이후 jwt token 생성
         */
        return null;
    }

    public void logout(String code) throws BaseException {
        kakaoOAuth.kakaoLogOut(code);
    }
}
