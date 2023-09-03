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

        // 1차 ID로 조회되지 않은 유저
        if (users == null) {

            // 2차 Email로 조회
            Optional<Users> users2 = usersService.findUsersByEmail(email);

            // 신규 유저일 경우
            if (users2.isEmpty()) {
                users = new Users();
                users.setEmail(email);
                users.setUsersName(userName);
                users.setPassword("dlatlfhsjgdjensmsvotmdnjem"); // 임의의 패스워드 지정

                // TODO : JPQL, EntityManager (영속성 컨테이너) 등 공부
                // TODO : 기본 정보 외 선택 정보 저장 단계를 추가해야함.

                // 1차적인 DB 유저 생성
                usersService.saveUser(users);

                // 생성된 DB 유저를 kakao 유저로 전환
                usersService.updateUsersToKakaoUsers(userId, email);
            }
        }

        // JWT 토큰 발급 단계
        try {
//            Long kakaoId = usersService.findUsersById(userId).getUsersId();
            String jwt = jwtService.createJwt(userId);
            return new PostLogInRes(userId, jwt);
//            return new PostLogInRes(kakaoId, jwt);
        } catch (Exception ignored) {
            throw new BaseException(BaseResponseStatus.PASSWORD_ENCRYPTION_ERROR);
        }
    }

    public void logout(String code) throws BaseException {
        kakaoOAuth.kakaoLogOut(code);
    }
}
