package com.swm.ventybackend.feed;

import com.swm.ventybackend.collection.Collection;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

@Data
@Getter
@NoArgsConstructor
@Entity
@Table(name = "feed")
public class Feed {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long feedId;

    @Column(name = "feed_title")
    private String feedTitle;

    @Column(name = "feed_context")
    private String feedContext;

    @Column(name = "status")
    @ColumnDefault("1")
    private Integer status;

    @Column(name = "users_id")
    private Long usersId;

    @Column(name = "collection_id")
    private Long collectionId;
}
