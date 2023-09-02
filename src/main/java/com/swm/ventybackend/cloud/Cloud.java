package com.swm.ventybackend.cloud;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

@Data
@Getter
@NoArgsConstructor
@Entity
@Table(name = "cloud")
public class Cloud {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long cloudId;

    @ColumnDefault("1")
    @Column(name = "status")
    private Integer status;

    @Column(name = "max_storage")
    private Double maxStorage = 0.0;

    @Column(name = "current_storage")
    private Double currentStorage = 0.0;

    @Column(name = "users_id")
    private Long usersId;

}
