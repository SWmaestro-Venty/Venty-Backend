package com.swm.ventybackend.privateClubDetail;



import jakarta.annotation.Nullable;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/privateClubDetail")
@RequiredArgsConstructor
public class PrivateClubDetailController {

    private final PrivateClubDetailService privateClubDetailService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/create")
    public String create(@RequestParam @Nullable String password, Integer maxUsers, Long usersId, Long clubId, String description) {
        PrivateClubDetail privateClubDetail = new PrivateClubDetail();
        privateClubDetail.setPrivateClubMaxUsers(maxUsers);
        privateClubDetail.setUsersId(usersId);
        privateClubDetail.setClubId(clubId);

        if (password != null)
            privateClubDetail.setPrivateClubPassword(password);

        if (description != null)
            privateClubDetail.setPrivateClubDescription(description);

        Long privateClubDetailId = privateClubDetailService.savePrivateClubDetail(privateClubDetail);
        return privateClubDetailId.toString();
    }

    @DeleteMapping("/delete")
    public String delete(@RequestParam Long id) {
        privateClubDetailService.removePrivateClubDetail(id);
        return id + "번 비공개 그룹 세부정보 삭제";
    }

    @GetMapping("/findPrivateClubDetailById")
    public PrivateClubDetail findPrivateClubDetailById(@RequestParam Long id) {
        return privateClubDetailService.findPrivateClubDetailById(id);
    }

    @GetMapping("/findPrivateClubDetailByClubId")
    public PrivateClubDetail findPrivateClubDetailByClubId(@RequestParam Long clubId) {
        Optional<PrivateClubDetail> pcd = privateClubDetailService.findPrivateClubDetailByClubId(clubId);
        if (pcd.isPresent())
            return pcd.get();
        else throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "PrivateClubDetail이 존재하지 않습니다.");
    }

    @GetMapping("/findPrivateClubDetailsByUsersId")
    public List<PrivateClubDetail> findPrivateClubDetailsByUsersId(@RequestParam Long usersId) {
        return privateClubDetailService.findPrivateClubDetailsByUsersId(usersId);
    }

    @GetMapping("/updatePrivateClubMaxUsersByClubId")
    public void updatePrivateClubMaxUsersByClubId(@RequestParam Long clubId, Integer maxUsers) {
        privateClubDetailService.updatePrivateClubMaxUsersByClubId(clubId, maxUsers);
    }

    @GetMapping("/updatePrivateClubOwnerByClubId")
    public void updatePrivateClubOwnerByClubId(@RequestParam Long clubId, Long usersId) {
        privateClubDetailService.updatePrivateClubOwnerByClubId(clubId, usersId);
    }

    @PostMapping("/passwordTest")
    public Object passwordTest(@RequestParam Long clubId, String password) {
        Optional<PrivateClubDetail> pcd = privateClubDetailService.findPrivateClubDetailByClubId(clubId);
        if (pcd.isPresent()) {
            if (pcd.get().checkPassword(password, passwordEncoder))
                return true;
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "비밀번호가 일치하지 않습니다.");
        }
        else throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "PrivateClubDetail이 존재하지 않습니다.");
    }
}


