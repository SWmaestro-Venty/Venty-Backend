package com.swm.ventybackend.content_map;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Data
@Getter
@NoArgsConstructor
@Entity
@Table(name = "content_map")
public class ContentMap {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long contentMapId;

    @Column(name = "content_id")
    private Long contentId;

    @Column(name = "collection_id")
    private Long collectionId;

    @Column(name = "feed_id")
    private Long feedId;
}
