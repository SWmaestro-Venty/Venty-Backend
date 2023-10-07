package com.swm.ventybackend.myTagMap;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@Getter
@Entity
@NoArgsConstructor
@Table(name = "my_tag_map")
public class MyTagMap {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long myTagMapId;

    @Column(name = "users_id")
    private Long usersId;

    @Column(name = "club_id")
    private Long clubId;

    @Column(name = "tag_id")
    private Long tagId;

}
