package com.swm.ventybackend.clubBasicTagMap;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@Getter
@Entity
@NoArgsConstructor
@Table(name = "club_basic_tag_map")
public class ClubBasicTagMap {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long clubBasicTagMapId;

    @Column(name = "club_id")
    private Long clubId;

    @Column(name = "tag_id")
    private Long tagId;
}
