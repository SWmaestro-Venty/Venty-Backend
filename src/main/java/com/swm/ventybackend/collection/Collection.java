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

//    @ManyToOne // Many = Collection, One = Group 하나의 그룹은 여러 컬렉션을 가질 수 있음
    @ManyToOne
    @JoinColumn(name = "club_id")
    private Club club;

    @OneToOne
    @JoinColumn(name = "users_id")
    private Users users;
}

// @TODO : collection은 만든 사람 정보를 포함해야 하며, 만들어진 collection -> love 테이블 생성