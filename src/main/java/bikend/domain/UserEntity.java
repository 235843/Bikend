package bikend.domain;


import bikend.utils.enums.UserRole;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "user")
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String firstName;
    private String lastName;
    @Column(unique = true)
    private String email;
    private String telephone;
    private String password;
    @Enumerated(EnumType.STRING)
    private UserRole role;
    @Column(unique = true)
    private String verificationToken;
    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    @JsonManagedReference
    private List<ReservationEntity> reservations;
    private boolean blocked;
    private boolean activated;

}

