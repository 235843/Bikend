package bikend.domain;


import bikend.utils.enums.UserRole;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

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
    @Column(unique = true)
    private String telephone;
    private String password;
    @Enumerated(EnumType.STRING)
    private UserRole role;
    @Column(unique = true)
    private String verificationToken;
    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<ReservationEntity> reservations;
    @ColumnDefault("false")
    private boolean blocked;
    @ColumnDefault("false")
    private boolean activated;

}

