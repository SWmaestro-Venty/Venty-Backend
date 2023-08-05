package com.swm.ventybackend.user;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.type.descriptor.jdbc.TinyIntJdbcType;
import org.joda.time.DateTime;

import java.sql.Timestamp;

@Data
@Getter
@NoArgsConstructor
@Entity
@Table
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    @Column(name = "email")
    private String email;

    @Column(name = "password")
    private String password;

    @Column(name = "user_name")
    private String userName;

    @Column(name = "gender")
    private Integer gender;

    @Column(name = "nickname")
    private String nickName;

    @Column(name = "status")
    private Integer status;

    @Basic
    @Column(name = "created_date")
    private Timestamp created_date;

    @Column(name = "deleted_date")
    private Timestamp deleted_date = null;

    @Builder
    public User(String email, String password, String userName, Integer gender, String nickName, Integer status, Timestamp created_date) {
        this.email = email;
        this.password = password;
        this.userName = userName;
        this.gender = gender;
        this.nickName = nickName;
        this.status = status;
        this.created_date = created_date;
        this.deleted_date = null;
    }
}
