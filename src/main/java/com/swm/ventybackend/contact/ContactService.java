package com.swm.ventybackend.contact;

import com.swm.ventybackend.contact.Contact;
import com.swm.ventybackend.contact.ContactRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class ContactService {

    private final ContactRepository contactRepository;

    public Long saveContact(Contact contact) {
        contactRepository.save(contact);
        return contact.getContactId();
    }

    public void removeContact(Long id) { contactRepository.remove(id); }

    public Contact findContactByContactId(Long id) { return contactRepository.findByContactId(id); }

    public Contact findContactByUsersId(String name) { return contactRepository.findByUsersId(name); }

    public List<Contact> findAllContact() { return contactRepository.findAll(); }

}
