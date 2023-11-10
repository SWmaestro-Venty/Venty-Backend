package com.swm.ventybackend.privateClubDetail;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;

@Data
@Getter
@Entity
@NoArgsConstructor
@Table(name = "private_club_detail")
public class PrivateClubDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long privateClubDetailId;

    @Column(name = "private_club_password")
    private String privateClubPassword;

    @Column(name = "private_club_max_users")
    private Integer privateClubMaxUsers;

    @Column(name = "private_club_description")
    private String privateClubDescription;

    @Column(name = "users_id")
    private Long usersId;

    @Column(name = "club_id")
    private Long clubId;

    public PrivateClubDetail hashPassword(PasswordEncoder passwordEncoder) {
        this.privateClubPassword = passwordEncoder.encode(this.privateClubPassword);
        return this;
    }

    public boolean checkPassword(String plainPassword, PasswordEncoder passwordEncoder) {
        return passwordEncoder.matches(plainPassword, this.privateClubPassword);
    }

}
