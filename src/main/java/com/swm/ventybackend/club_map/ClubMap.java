package com.swm.ventybackend.club_map;


import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Data
@Getter
@NoArgsConstructor
@Entity
@Table(name = "club_map")
public class ClubMap {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long clubMapId;

    @Column(name = "users_id")
    private Long usersId;

    @Column(name = "club_id")
    private Long clubId;

}
