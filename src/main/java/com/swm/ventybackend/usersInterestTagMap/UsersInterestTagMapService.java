package com.swm.ventybackend.usersInterestTagMap;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class UsersInterestTagMapService {

    private final UsersInterestTagMapRepository usersInterestTagMapRepository;

    public Long saveUsersInterestTagMap(UsersInterestTagMap usersInterestTagMap) {
        usersInterestTagMapRepository.save(usersInterestTagMap);
        return usersInterestTagMap.getUsersInterestTagMapId();
    }

    public void removeUsersInterestTagMap(Long id) {
        usersInterestTagMapRepository.remove(id);
    }

    public UsersInterestTagMap findUsersInterestTagMapById(Long id) {
        return usersInterestTagMapRepository.findById(id);
    }

    public List<UsersInterestTagMap> findUsersInterestTagMapsByUsersId(Long usersId) {
        return usersInterestTagMapRepository.findUsersInterestTagMapsByUsersId(usersId);
    }
}
