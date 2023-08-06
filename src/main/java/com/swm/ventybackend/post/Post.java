package com.swm.ventybackend.post;

import com.swm.ventybackend.board.Board;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Data
@Getter
@NoArgsConstructor
@Entity
@Table(name = "post")
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long postId;

    @Column(name = "post_category")
    private Integer category;

    @Column(name = "post_title")
    private String title;

    @Column(name = "post_context")
    private String context;

    @Column(name = "status")
    private Integer status;

    @ManyToOne
    @JoinColumn(name = "board_id")
    private Board board;

}
