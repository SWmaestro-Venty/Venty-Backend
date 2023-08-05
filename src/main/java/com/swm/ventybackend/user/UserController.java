package com.swm.ventybackend.user;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {

    private final UserRepository userRepository;

    @PersistenceContext
    private EntityManager entityManager;

    @GetMapping("getAll")
    public String searchAllUser() {
        return userRepository.searchAllUser().toString();
    }
}
