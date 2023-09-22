package com.swm.ventybackend.users;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
@RequiredArgsConstructor
public class UsersRepository {

    private final EntityManager em;

    public void save(Users users) {
        em.persist(users);
    }

    public void remove(Long id) {
        em.remove(findById(id));
    }

    public Users findById(Long id) {
        return em.find(Users.class, id);
    }

    public Users findByName(String findName) {
        return em.createQuery("SELECT users FROM Users users WHERE users.usersName = :findName", Users.class)
                .setParameter("findName", findName)
                .getSingleResult();
    }

    public Optional<Users> findByEmail(String email) {
        List<Users> users = em.createQuery("SELECT users FROM Users users WHERE users.email = :email", Users.class)
                .setParameter("email", email)
                .getResultList();
        return users.stream().findAny();
    }

    public List<Users> findAll() {
        return em.createQuery("SELECT users FROM Users users", Users.class)
                .getResultList();
    }

//    public Users updateUsersToKakaoUsers(Long kakaoId, String email) {
//        Users users = findByEmail(email).get();
//        users = em.find(Users.class, users.getUsersId());
//        users.setUsersId(kakaoId);
//        return users;
//    }

    public Optional<Users> findUsersByOAuthIdAndType(Long oAuthId, String oAuthType) {
        List<Users> users = em.createQuery("SELECT users FROM Users users WHERE users.oAuthId = :oAuthId AND users.oAuthType = :oAuthType", Users.class)
                .setParameter("oAuthId", oAuthId)
                .setParameter("oAuthType", oAuthType)
                .getResultList();
        return users.stream().findAny();
    }

    public void updateUsersToKakaoUsers(Long kakaoId, String email) {
        em.createQuery("UPDATE Users users SET users.usersId = :kakaoId WHERE users.email = :email")
                .setParameter("kakaoId", kakaoId)
                .setParameter("email", email)
                .executeUpdate();
        em.clear();
    }

    public void updateUsersNicknameByUsersId(Long usersId, String nickname) {
        em.createQuery("UPDATE Users users SET users.nickName = :nickname WHERE users.usersId = :usersId")
                .setParameter("usersId", usersId)
                .setParameter("nickname", nickname)
                .executeUpdate();
        em.clear();
    }

    public void updateUsersProfileImageUrlByUsersId(Long usersId, String profileImageUrl) {
        em.createQuery("UPDATE Users users SET users.profileImageUrl = :profileImageUrl WHERE users.usersId = :usersId")
                .setParameter("usersId", usersId)
                .setParameter("profileImageUrl", profileImageUrl)
                .executeUpdate();
        em.clear();
    }
}
