package com.swm.ventybackend.collection.service.impl;

import com.swm.ventybackend.collection.service.CollectionService;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class CollectionServiceImpl implements CollectionService {

    @Override
    public Map<String, Object> getCollectionData() {

        Map<String, Object> collectionData = new HashMap<>();

        collectionData.put("label1", "check1");
        collectionData.put("label2", "check2");
        collectionData.put("label3", "check3");

        return collectionData;
    }
}
