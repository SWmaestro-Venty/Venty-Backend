package com.swm.ventybackend.users;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class UsersService {

    private final UsersRepository usersRepository;

    public Long saveUser(Users users) {
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

    public List<Users> findAllUsers() {
        return usersRepository.findAll();
    }
}
