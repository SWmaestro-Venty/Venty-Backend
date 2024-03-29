package com.swm.ventybackend.users;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.math.BigInteger;
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
    private String gender;

    @Column(name = "nickname")
    private String nickName;

    @Column(name = "status")
    private Integer status = 1;

    @CreationTimestamp
    @Column(name = "created_date")
    private Timestamp createdDate;

    @Column(name = "deleted_date")
    @ColumnDefault("null")
    private Timestamp deletedDate;

    @Column(name = "profile_image_url")
    private String profileImageUrl;

    @Column(name = "oauth_type")
    private String oAuthType;

    @Column(name = "oauth_id")
    private String oAuthId;

    @Column(name = "age_range")
    private String ageRange;

    public Users hashPassword(PasswordEncoder passwordEncoder) {
        this.password = passwordEncoder.encode(this.password);
        return this;
    }

    public boolean checkPassword(String plainPassword, PasswordEncoder passwordEncoder) {
        return passwordEncoder.matches(plainPassword, this.password);
    }
}
