package com.swm.ventybackend.myTagMap;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class MyTagMapService {

    private final MyTagMapRepository myTagMapRepository;

    public Long saveMyTagMap(MyTagMap myTagMap) {
        myTagMapRepository.save(myTagMap);
        return myTagMap.getMyTagMapId();
    }

    public void removeMyTagMap(Long id) {
        myTagMapRepository.remove(id);
    }

    public MyTagMap findMyTagMapById(Long id) {
        return myTagMapRepository.findById(id);
    }

    public List<MyTagMap> findMyTagMapsByUsersId(Long usersId) {
        return myTagMapRepository.findMyTagMapsByUsersId(usersId);
    }
}
