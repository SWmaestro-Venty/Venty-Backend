package com.swm.ventybackend.usersTerm;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class UsersTermService {
    private final UsersTermRepository usersTermRepository;

    public Long saveUsersTerm(UsersTerm usersTerm) {
        usersTermRepository.save(usersTerm);
        return usersTerm.getUsersTermId();
    }

    public void removeUsersTerm(Long id) {
        usersTermRepository.remove(id);
    }

    public UsersTerm findUsersTermById(Long id) {
        return usersTermRepository.findById(id);
    }

    public List<UsersTerm> findUsersTermByUsersId(Long usersId) {
        return usersTermRepository.findUsersTermByUsersId(usersId);
    }
}
