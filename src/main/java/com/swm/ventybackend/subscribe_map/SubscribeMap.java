package com.swm.ventybackend.subscribe_map;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Data
@Getter
@NoArgsConstructor
@Entity
@Table(name = "subscribe_map")
public class SubscribeMap {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long subscribeMapId;

    @Column(name = "users_id")
    private Long usersId;

    @Column(name = "collection_id")
    private Long collectionId;
}
