package com.swm.ventybackend.subscribe_map;

import jakarta.annotation.Nullable;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/subscribeMap")
@RequiredArgsConstructor
public class SubscribeMapController {

    private final SubscribeMapService subscribeMapService;

    @PostMapping("/create")
    public String create(@RequestParam Long usersId, Long collectionId) {
        if (subscribeMapService.checkExistSubscribeMapByUsersIdAndCollectionId(usersId, collectionId)) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "이미 존재하는 SubsscribeMap 입니다.");
        }
        SubscribeMap subscribeMap = new SubscribeMap();
        subscribeMap.setUsersId(usersId);
        subscribeMap.setCollectionId(collectionId);

        Long subscribeMapId = subscribeMapService.saveSubscribeMap(subscribeMap);

        return subscribeMapId + "번 맵 생성 완료";
    }

    @DeleteMapping("/delete")
    public String delete(@RequestParam Long id) {
        subscribeMapService.removeSubscribeMap(id);
        return id + "번 콘텐츠 맵 삭제 완료";
    }

    @GetMapping("/findById")
    public String read(@RequestParam Long id) {
        return subscribeMapService.findSubscribeMapById(id).toString();
    }

    @GetMapping("/all")
    public List<SubscribeMap> readAll() {
        return subscribeMapService.findAllSubscribeMap();
    }

    @GetMapping("/findSubScribeMapsByUsersId")
    public List<SubscribeMap> findSubScribeMapsByUsersId(@RequestParam Long usersId) {
        return subscribeMapService.findSubscribeMapsByUsersId(usersId);
    }

}
