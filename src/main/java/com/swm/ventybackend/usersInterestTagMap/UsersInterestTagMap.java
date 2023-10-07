package com.swm.ventybackend.usersInterestTagMap;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@Getter
@NoArgsConstructor
@Entity
@Table(name = "users_interest_tag_map")
public class UsersInterestTagMap {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long usersInterestTagMapId;

    @Column(name = "users_id")
    private Long usersId;

    @Column(name = "tag_id")
    private Long tagId;

}
