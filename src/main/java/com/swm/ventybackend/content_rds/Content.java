package com.swm.ventybackend.content_rds;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import java.sql.Timestamp;

@Data
@Getter
@NoArgsConstructor
@Entity
@Table(name = "content")

public class Content {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long contentId;

    @Column(name = "original_url")
    private String originalUrl;

    @Column(name = "thumbnail_url")
    private String thumbnailUrl;

    @Column(name = "created_date")
    @CreationTimestamp
    private Timestamp createdDate;

    @Column(name = "status")
    private Integer status;

    @Column(name = "is_image_or_video")
    private Integer isImageOrVideo;

    @Column(name = "extension")
    private String extension;

    @Column(name = "size")
    private String size;

    @Column(name = "users_id")
    private Long usersId;

    @Column(name = "download_count")
    private Integer downloadCount;

}
