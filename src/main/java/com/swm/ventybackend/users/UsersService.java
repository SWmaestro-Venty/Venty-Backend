package com.swm.ventybackend.users;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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

    public Optional<Users> findUsersByEmail(String email) { return usersRepository.findByEmail(email); }

    public Optional<Users> findUsersByOAuthIdAndType(String oAuthId, String oAuthType) {
        return usersRepository.findUsersByOAuthIdAndType(oAuthId, oAuthType);
    }

    public List<Users> findAllUsers() {
        return usersRepository.findAll();
    }

    public void updateUsersToKakaoUsers(Long kakaoId, String email) {
        usersRepository.updateUsersToKakaoUsers(kakaoId, email);
    }

    public void updateUsersNicknameByUsersId(Long usersId, String nickname) {
        usersRepository.updateUsersNicknameByUsersId(usersId, nickname);
    }

    public void updateUsersProfileImageUrl(Long usersId, String profileImageUrl) {
        usersRepository.updateUsersProfileImageUrlByUsersId(usersId, profileImageUrl);
    }
}
