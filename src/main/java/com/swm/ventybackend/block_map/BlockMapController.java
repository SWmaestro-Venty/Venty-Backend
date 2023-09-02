package com.swm.ventybackend.block_map;


import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Description;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/block")
@RequiredArgsConstructor
public class BlockMapController {

    private final BlockMapService blockMapService;

    @PostMapping("/create")
    public String create(@RequestParam Long usersId, Long blockedUsersId) {
        BlockMap blockMap = new BlockMap();
        blockMap.setUsersId(usersId);
        blockMap.setBlockedUsersId(blockedUsersId);

        Long blockMapId = blockMapService.saveBlockMap(blockMap);
        return blockMapId + "번 차단 등록 완료";
    }

    @DeleteMapping("/deleteByBlockMapId")
    public String delete(@RequestParam Long blockMapId) {
        blockMapService.removeBlockMapByBlockMapId(blockMapId);
        return blockMapId + "번 차단 맵 삭제 완료";
    }

    @DeleteMapping("/deleteByBothId")
    public String delete(@RequestParam Long usersId, Long blockedUsersId) {
        blockMapService.removeByBothId(usersId, blockedUsersId);
        return usersId +"번 유저, " + blockedUsersId + "번 차단 유저 맵 삭제";
    }

    @GetMapping("/findByUsersId")
    public List<BlockMap> findByUsersId(@RequestParam Long usersId) {
        return blockMapService.findByUsersId(usersId);
    }

    @GetMapping("/findByBlockedUsersByUsersId")
    public List<BlockMap> findByBlockedUsersByUsersId(@RequestParam Long blockedUsersId) {
        return blockMapService.findByBlockedUsersByUsersId(blockedUsersId);
    }

}
