package com.swm.ventybackend.user;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;
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

    @ColumnDefault("1")
    @Column(name = "status")
    private Integer status;

    @CreationTimestamp
    @Column(name = "created_date")
    private Timestamp createdDate;

    @Column(name = "deleted_date")
    private Timestamp deletedDate = null;

}
