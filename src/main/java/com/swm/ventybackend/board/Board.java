package com.swm.ventybackend.board;

import com.swm.ventybackend.club.Club;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Data
@Getter
@NoArgsConstructor
@Entity
@Table(name = "board")
public class Board {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long boardId;

    @Column(name = "board_name")
    private String name;

    @Column(name = "board_category")
    private Integer category;

    @OneToOne
    @JoinColumn(name = "club_id")
    private Club club;
}
