package com.swm.ventybackend.cloud;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class CloudService {

    private final CloudRepository cloudRepository;

    public Long saveCloud(Cloud cloud) {
        cloudRepository.save(cloud);
        return cloud.getCloudId();
    }

    public void removeCloud(Long id) {
        cloudRepository.removeCloud(id);
    }

    public Cloud findCloudByUsersId(Long usersId) {
        return cloudRepository.findCloudByUsersId(usersId);
    }

    public Cloud findCloudByCloudId(Long cloudId) {
        return cloudRepository.findByCloudId(cloudId);
    }

    public List<Cloud> findCloudsByStatus(Integer status) {
        return cloudRepository.findCloudsByStatus(status);
    }

}
