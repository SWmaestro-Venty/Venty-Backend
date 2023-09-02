package com.swm.ventybackend.block_map;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class BlockMapService {

    private final BlockMapRepository blockMapRepository;

    public Long saveBlockMap(BlockMap blockMap) {
        blockMapRepository.save(blockMap);
        return blockMap.getBlock_id();
    }

    public void removeBlockMapByBlockMapId(Long blockMapId) {
        blockMapRepository.remove(blockMapId);
    }

    public List<BlockMap> findByUsersId(Long usersId) {
        return blockMapRepository.findByUsersId(usersId);
    }

    public List<BlockMap> findByBlockedUsersByUsersId(Long blockedUsersId) {
        return blockMapRepository.findByBlockedUsersByUsersId(blockedUsersId);
    }

    public void removeByBothId(Long usersId, Long blockedUsersId) {
        blockMapRepository.removeByBothId(usersId, blockedUsersId);
    }
}
