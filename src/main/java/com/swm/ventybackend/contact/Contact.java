package com.swm.ventybackend.contact;


import com.swm.ventybackend.users.Users;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.sql.Timestamp;

@Data
@Getter
@NoArgsConstructor
@Entity
@Table(name = "contact")
public class Contact {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long contactId;

    @Column(name = "category")
    private Integer category;

    @Column(name = "created_date")
    @CreationTimestamp
    private Timestamp createdDate;

    @Column(name = "status")
    private Integer status;

    @Column(name = "title")
    private String title;

    @Column(name = "contact_context")
    private String contactContext;

    @ManyToOne
    @JoinColumn(name = "users_id")
    private Users users;
}
