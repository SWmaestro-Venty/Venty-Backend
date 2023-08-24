package com.swm.ventybackend.users;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class UsersService {

    private final UsersRepository usersRepository;
    private final PasswordEncoder bCryptPasswordEncoder;

    public Long saveUser(Users users) {
        users.hashPassword(bCryptPasswordEncoder);
        usersRepository.save(users);
        return users.getUsersId();
    }

    public void removeUser(Long id) {
        usersRepository.remove(id);
    }

    public Users findUsersById(Long id) {
        return usersRepository.findById(id);
    }

    public Users findUsersByName(String name) {
        return usersRepository.findByName(name);
    }

    public Users findUsersByEmail(String email) { return usersRepository.findByEmail(email); }

    public List<Users> findAllUsers() {
        return usersRepository.findAll();
    }

    // @TODO : 중복 여부 검사
}
