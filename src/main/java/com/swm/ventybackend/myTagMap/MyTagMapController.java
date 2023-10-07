package com.swm.ventybackend.myTagMap;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/myTagMap")
@RequiredArgsConstructor
public class MyTagMapController {

    private final MyTagMapService myTagMapService;

    @PostMapping("/create")
    public String create(@RequestParam Long usersId, Long clubId, Long tagId) {
        MyTagMap myTagMap = new MyTagMap();
        myTagMap.setUsersId(usersId);
        myTagMap.setClubId(clubId);
        myTagMap.setTagId(tagId);

        Long myTagMapId = myTagMapService.saveMyTagMap(myTagMap);
        return myTagMapId.toString();
    }

    @DeleteMapping("/delete")
    public String delete(@RequestParam Long id) {
        myTagMapService.removeMyTagMap(id);
        return id + "번 마이 태그 맵 삭제 완료";
    }

    @GetMapping("/findMyTagMapById")
    public MyTagMap findMyTagMapById(@RequestParam Long id) {
        return myTagMapService.findMyTagMapById(id);
    }

    @GetMapping("/findMyTagMapsByUsersId")
    public List<MyTagMap> findMyTagMapsByUsersId(@RequestParam Long usersId) {
        return myTagMapService.findMyTagMapsByUsersId(usersId);
    }
}
