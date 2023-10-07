package com.swm.ventybackend.usersInterestTagMap;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/usersInterestTagMap")
@RequiredArgsConstructor
public class UsersInterestTagMapController {

    private final UsersInterestTagMapService usersInterestTagMapService;

    @PostMapping("/create")
    public String create(@RequestParam Long usersId, Long tagId) {
        UsersInterestTagMap usersInterestTagMap = new UsersInterestTagMap();
        usersInterestTagMap.setUsersId(usersId);
        usersInterestTagMap.setTagId(tagId);

        Long usersInterestTagMapId = usersInterestTagMapService.saveUsersInterestTagMap(usersInterestTagMap);
        return usersInterestTagMapId.toString();
    }

    @DeleteMapping("/delete")
    public String delete(@RequestParam Long id) {
        usersInterestTagMapService.removeUsersInterestTagMap(id);
        return id + "번 클럽 기본 태그 맵 삭제";
    }

    @GetMapping("/findUsersInterestTagMapById")
    public UsersInterestTagMap findUsersInterestTagMapById(@RequestParam Long id) {
        return usersInterestTagMapService.findUsersInterestTagMapById(id);
    }

    @GetMapping("/findUsersInterestTagMapsByUsersId")
    public List<UsersInterestTagMap> findUsersInterestTagMapsByUsersId(@RequestParam Long usersId) {
        return usersInterestTagMapService.findUsersInterestTagMapsByUsersId(usersId);
    }
}
