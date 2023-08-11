package com.swm.ventybackend.tagMap;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Data
@Getter
@NoArgsConstructor
@Entity
@Table(name = "tag_map")
public class TagMap {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long tagMapId;

    @Column(name = "tag_id")
    private Long tagId;

    @Column(name = "content_id")
    private Long contentId;

}
