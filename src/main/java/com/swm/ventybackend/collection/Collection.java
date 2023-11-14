package com.swm.ventybackend.collection;

import com.swm.ventybackend.club.Club;
import com.swm.ventybackend.users.Users;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;

import java.sql.Timestamp;

@Data
@Getter
@NoArgsConstructor
@Entity
@Table(name = "collection")
public class Collection {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long collectionId;

    @Column(name = "collection_name")
    private String collectionName;

    @Column(name = "created_date")
    @CreationTimestamp // 입력될 때 자동으로 시간 주입
    private Timestamp created_date;

    @Column(name = "status")
    @ColumnDefault("1")
    private Integer status;

    @Column(name = "thumbnail_image_url")
    @ColumnDefault("null")
    private String thumbnailImageUrl;

    @Column(name = "club_id")
    private Long clubId;

    @Column(name = "users_id")
    private Long usersId;

    @Column(name = "collection_description")
    private String collectionDescription;

    @Column(name = "likes")
    private Integer likes;
}