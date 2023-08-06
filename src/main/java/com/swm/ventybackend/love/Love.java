package com.swm.ventybackend.love;


import com.swm.ventybackend.collection.Collection;
import com.swm.ventybackend.users.Users;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Data
@Getter
@NoArgsConstructor
@Entity
@Table(name = "love")
public class Love {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long loveId;

//    @OneToOne
//    @JoinColumn(name = "feed_id")
//    private Feed feed;
//
    @OneToOne
    @JoinColumn(name = "collection_id")
    private Collection collection;
//
//    @OneToOne
//    @JoinColumn(name = "post_id")
//    private Post post;

    @OneToOne
    @JoinColumn(name = "users_id")
    private Users users;

//    @OneToOne
//    @JoinColumn(name = "comment_id")
//    private Comment comment;
}

// @TODO : 나머지 연결
