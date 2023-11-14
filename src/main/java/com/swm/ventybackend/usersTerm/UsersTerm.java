package com.swm.ventybackend.usersTerm;


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
@Table(name = "users_term")
public class UsersTerm {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long usersTermId;

    @Column(name = "term1")
    private Boolean term1;

    @Column(name = "term2")
    private Boolean term2;

    @CreationTimestamp
    @Column(name = "edited_at")
    private Timestamp editedAt;

    @Column(name = "users_id")
    private Long usersId;
}
