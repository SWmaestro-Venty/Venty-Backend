package com.swm.ventybackend.collection.controller;

import com.swm.ventybackend.collection.service.CollectionService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController // @Controller + @ResponseBody = Json 형태로 객체 데이터 반환 역할
@RequiredArgsConstructor // final | @NotNull이 붙은 필드 생성자를 자동 생성
public class CollectionController {

    private final CollectionService collectionService;

    @GetMapping("/collection")
    public Map<String, Object> CollectionController() {
        return collectionService.getCollectionData();
    }
}
