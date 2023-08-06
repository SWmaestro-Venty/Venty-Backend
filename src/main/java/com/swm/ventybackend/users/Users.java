package com.swm.ventybackend.users;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;

import java.sql.Timestamp;

@Data
@Getter
@NoArgsConstructor
@Entity
@Table(name = "users")
public class Users {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long usersId;

    @Column(name = "email")
    private String email;

    @Column(name = "password")
    private String password;

    @Column(name = "users_name")
    private String usersName;

    @Column(name = "gender")
    private Integer gender;

    @Column(name = "nickname")
    private String nickName;

    @ColumnDefault("1")
    @Column(name = "status")
    private Integer status;

    @CreationTimestamp
    @Column(name = "created_date")
    private Timestamp createdDate;

    @Column(name = "deleted_date")
    private Timestamp deletedDate = null;

}