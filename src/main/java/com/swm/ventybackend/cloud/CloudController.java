package com.swm.ventybackend.cloud;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cloud")
@RequiredArgsConstructor
public class CloudController {
    private final CloudService cloudService;

    @PostMapping("/create")
    public String create(@RequestParam Long usersId) {
        Cloud cloud = new Cloud();
        cloud.setUsersId(usersId);

        Long cloudId = cloudService.saveCloud(cloud);
        return cloudId +"번 초기 클라우드를 " + usersId + "번 유저에게 할당";
    }

    @DeleteMapping("/delete")
    public String delete(@RequestParam Long cloudId) {
        cloudService.removeCloud(cloudId);
        return cloudId + "번 클라우드 삭제 완료";
    }

    @GetMapping("/findCloudByCloudId")
    public Cloud findCloudByCloudId(@RequestParam Long cloudId) {
        return cloudService.findCloudByCloudId(cloudId);
    }

    @GetMapping("/findCloudByUsersId")
    public Cloud findCloudByUsersId(@RequestParam Long usersId) {
        return cloudService.findCloudByUsersId(usersId);
    }

    @GetMapping("/findCloudsByStatus")
    public List<Cloud> findCloudsByStatus(@RequestParam Integer status) {
        return cloudService.findCloudsByStatus(status);
    }

}
