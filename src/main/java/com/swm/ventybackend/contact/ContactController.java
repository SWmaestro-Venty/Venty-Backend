package com.swm.ventybackend.contact;

import com.swm.ventybackend.contact.Contact;
import com.swm.ventybackend.contact.ContactService;
import com.swm.ventybackend.users.Users;
import com.swm.ventybackend.users.UsersService;
import jakarta.annotation.Nullable;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/contact")
@RequiredArgsConstructor
public class ContactController {

    private final ContactService contactService;
    private final UsersService usersService;

    @PostMapping("/create")
    public String create(@RequestParam Integer category, @Nullable Integer status, String title,
                         String contactContext, Long usersId) {
        Contact contact = new Contact();
        contact.setCategory(category);
        if (status != null) contact.setStatus(status);
        contact.setTitle(title);
        contact.setContactContext(contactContext);

        Users users = usersService.findUsersById(usersId);
        contact.setUsers(users);

        Long contactId = contactService.saveContact(contact);
        return contactId + "번 문의 등록 완료";
    }

    @DeleteMapping("/delete")
    public String delete(@RequestParam Long id) {
        contactService.removeContact(id);
        return id + "번 문의 삭제 완료";
    }

    @GetMapping("/findByContactIdorUsersId")
    public String read(@RequestParam @Nullable Long id, String name) {
        if(id != null) {
            return contactService.findContactByContactId(id).toString();
        } else {
            return contactService.findContactByUsersId(name).toString();
        }
    }

    @GetMapping("/all")
    public String readAll() {
        return contactService.findAllContact().toString();
    }
}
