package com.swm.ventybackend.privateClubDetail;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class PrivateClubDetailService {

    private final PrivateClubDetailRepository privateClubDetailRepository;
    private final PasswordEncoder bCryptPasswordEncoder;

    public Long savePrivateClubDetail(PrivateClubDetail privateClubDetail) {
        privateClubDetail.hashPassword(bCryptPasswordEncoder);
        privateClubDetailRepository.save(privateClubDetail);
        return privateClubDetail.getPrivateClubDetailId();
    }

    public void removePrivateClubDetail(Long id) {
        privateClubDetailRepository.remove(id);
    }

    public PrivateClubDetail findPrivateClubDetailById(Long id) {
        return privateClubDetailRepository.findById(id);
    }

    public Optional<PrivateClubDetail> findPrivateClubDetailByClubId(Long clubId) {
        return privateClubDetailRepository.findPrivateClubDetailByClubId(clubId);
    }

    public List<PrivateClubDetail> findPrivateClubDetailsByUsersId(Long usersId) {
        return privateClubDetailRepository.findPrivateClubDetailsByUsersId(usersId);
    }

    public void updatePrivateClubMaxUsersByClubId(Long clubId, Integer maxUsers) {
        privateClubDetailRepository.updatePrivateClubMaxUsersByClubId(clubId, maxUsers);
    }

    public void updatePrivateClubOwnerByClubId(Long clubId, Long usersId) {
        privateClubDetailRepository.updatePrivateClubOwnerByClubId(clubId, usersId);
    }

}
