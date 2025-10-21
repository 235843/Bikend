package bikend.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

import java.util.Date;
import java.util.List;

@Entity
@Setter
@Getter
@Table(name = "reservation")
public class ReservationEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinTable(
            name = "user_reservations",
            joinColumns = @JoinColumn(name = "reservation_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private UserEntity user;
    private double cost;
    @OneToMany(fetch = FetchType.EAGER)
    private List<BikeEntity> bikeList;
    private Date reservationStart;
    private Date reservationStop;
    private Date createdAt;
    private Date updatedAt;
    @ColumnDefault("false")
    private boolean paid;
    @ColumnDefault("false")
    private boolean cancelled;
}
