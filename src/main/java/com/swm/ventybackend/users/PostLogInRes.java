package com.swm.ventybackend.users;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class PostLogInRes {
    long    userId;
    String  jwt;
}