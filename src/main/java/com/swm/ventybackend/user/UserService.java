package com.swm.ventybackend.user;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public Long saveUser(User user) {
        userRepository.save(user);
        return user.getUserId();
    }

    public void removeUser(Long id) {
        userRepository.remove(id);
    }

    public User findUserById(Long id) {
        return userRepository.findById(id);
    }

    public User findUserByName(String name) {
        return userRepository.findByName(name);
    }

    public List<User> findAllUser() {
        return userRepository.findAll();
    }
}
