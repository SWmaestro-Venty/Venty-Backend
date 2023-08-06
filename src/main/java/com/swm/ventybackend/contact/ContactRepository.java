package com.swm.ventybackend.contact;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class ContactRepository {

    @PersistenceContext
    private final EntityManager em;

    public void save(Contact contact) { em.persist(contact);}

    public void remove(Long id) { em.remove(findByContactId(id)); }

    public Contact findByContactId(Long id) { return em.find(Contact.class, id); }

    public Contact findByUsersId(String findId) {
        return em.createQuery("SELECT contact FROM Contact contact WHERE contact.users.usersId = :findId", Contact.class)
                .setParameter("findId", findId)
                .getSingleResult();
    }

    public List<Contact> findAll() {
        return em.createQuery("SELECT contact FROM Contact contact", Contact.class)
                .getResultList();
    }
}
