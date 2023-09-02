package com.swm.ventybackend.config.auth;

import com.swm.ventybackend.config.BaseResponse;
import com.swm.ventybackend.config.BaseException;
import com.swm.ventybackend.config.auth.OAuthService;

import com.swm.ventybackend.users.PostLogInRes;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("/oauth/kakao")
@AllArgsConstructor
public class OAuthController {
    @Autowired
    private final OAuthService oAuthService;

    @ResponseBody
    @GetMapping("")
    public BaseResponse<PostLogInRes> kakaoLogIn(@RequestParam("code") String code) {
        try {
            return new BaseResponse<>(oAuthService.kakaoLogin(code));
        } catch (BaseException baseException) {
            return new BaseResponse<>(baseException.getStatus());
        }
    }

    @ResponseBody
    @GetMapping("/log-out")
    public BaseResponse<String> kakaoLogOut(@RequestParam("code") String code) {
        try {
            String res = "로그아웃에 성공하였습니다.";
            oAuthService.logout(code);
            return new BaseResponse<String>(res);
        } catch (BaseException baseException) {
            return new BaseResponse<>(baseException.getStatus());
        }
    }
}