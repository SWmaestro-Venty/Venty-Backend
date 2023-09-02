package com.swm.ventybackend.config.auth.kakao;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class KakaoUser {
    Long userId;
    String email;
    String name;
}
