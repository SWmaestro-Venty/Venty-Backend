package com.swm.ventybackend.users;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class LoginUsersDTO {
    private Long usersId;
    private boolean isNewUsers;
    private String jwtToken;
}
