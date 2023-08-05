package com.swm.ventybackend.club;

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
@Table(name = "club")
public class Club {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long clubId;

    @Column(name = "category")
    private Integer category;

    @Column(name = "club_name")
    private String clubName;

    @Column(name = "created_date")
    @CreationTimestamp
    private Timestamp createdDate;

    @Column(name = "deleted_date")
    private Timestamp deletedDate = null;

    @Column(name = "status")
    @ColumnDefault("1")
    private Integer status;
}
