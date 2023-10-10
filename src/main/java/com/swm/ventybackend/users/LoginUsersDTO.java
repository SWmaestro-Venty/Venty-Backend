package com.swm.ventybackend.users;

import com.swm.ventybackend.utils.Token;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class LoginUsersDTO {
    private Long usersId;
    private boolean isNewUsers;
    private Token jwtToken;
}
