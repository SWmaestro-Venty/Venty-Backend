package com.swm.ventybackend.users;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class GetUserRes {
    private long    userId;
    private String  userName;
    private String  email;
    private String  password;
}