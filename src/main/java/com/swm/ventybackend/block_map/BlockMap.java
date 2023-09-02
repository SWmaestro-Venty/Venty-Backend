package com.swm.ventybackend.block_map;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@Getter
@NoArgsConstructor
@Entity
@Table(name = "block_map")
public class BlockMap {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long block_id;

    @Column(name = "users_id")
    private Long usersId;

    @Column(name = "blocked_users_id")
    private Long blockedUsersId;

}
