package com.swm.ventybackend.collection;

import com.amazonaws.services.dynamodbv2.xspec.L;
import com.swm.ventybackend.users.Users;
import com.swm.ventybackend.users.UsersRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class CollectionService {

    private final CollectionRepository collectionRepository;
    private final UsersRepository usersRepository;

    public Long saveCollection(Collection collection) {
        collectionRepository.save(collection);
        return collection.getCollectionId();
    }

    public void removeCollection(Long id) { collectionRepository.remove(id); }

    public Collection findCollectionById(Long id) { return collectionRepository.findById(id); }

    public Collection findCollectionByName(String name) { return collectionRepository.findByName(name); }

    public List<Collection> findCollectionByUsersId(Long usersId) {
        return collectionRepository.findByUsersId(usersId);
    }

    public List<Collection> findAllCollection() { return collectionRepository.findAll(); }

}
