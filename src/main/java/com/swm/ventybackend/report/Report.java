package com.swm.ventybackend.report;

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
@Table(name = "report")
public class Report {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long reportId;

    @Column(name = "black_id")
    private Long blackId;

    @ColumnDefault("0")
    @Column(name = "category")
    private Integer category;

    @Column(name = "context")
    private String context;

    @ColumnDefault("0")
    @Column(name = "process")
    private Integer process;

    @ColumnDefault("")
    @Column(name = "result")
    private String result;

    @CreationTimestamp
    @Column(name = "created_date")
    private Timestamp createdDate;

    @Column(name = "completed_date")
    private Timestamp completedDate;

    // 신고 대상 id (게시글, 컨텐츠 등)
    @Column(name = "black_thing_id")
    private Long blackThingId;

    // 신고자 id
    @Column(name = "users_id")
    private Long usersId;


}
