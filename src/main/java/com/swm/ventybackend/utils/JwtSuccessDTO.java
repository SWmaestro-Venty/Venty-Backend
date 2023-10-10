package com.swm.ventybackend.utils;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class JwtSuccessDTO {
    private String usersId;
    private String status;
}
