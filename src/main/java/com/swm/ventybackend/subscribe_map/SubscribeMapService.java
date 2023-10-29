package com.swm.ventybackend.subscribe_map;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class SubscribeMapService {

    private final SubscribeMapRepository subscribeMapRepository;

    public Long saveSubscribeMap(SubscribeMap subscribeMap) {
        subscribeMapRepository.save(subscribeMap);
        return subscribeMap.getSubscribeMapId();
    }

    public void removeSubscribeMap(Long id) {
        subscribeMapRepository.remove(id);
    }

    public SubscribeMap findSubscribeMapById(Long id) {
        return subscribeMapRepository.findById(id);
    }

    public List<SubscribeMap> findAllSubscribeMap() {
        return subscribeMapRepository.findAll();
    }

    public List<SubscribeMap> findSubscribeMapsByUsersId(Long usersId) {
        return subscribeMapRepository.findSubscribeMapsByUsersId(usersId);
    }

    public Boolean checkExistSubscribeMapByUsersIdAndCollectionId(Long usersId, Long collectionId) {
        return subscribeMapRepository.checkExistSubscribeMapByUsersIdAndCollectionId(usersId, collectionId);
    }

}
