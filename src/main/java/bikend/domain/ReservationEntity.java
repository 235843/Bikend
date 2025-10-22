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
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity user;
    private double cost;
    @ManyToMany(fetch = FetchType.EAGER, cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JoinTable(
            name = "reservation_bikes",
            joinColumns = @JoinColumn(name = "reservation_id"),
            inverseJoinColumns = @JoinColumn(name = "bike_id")
    )
    private List<BikeEntity> bikeList;
    private Date reservationStart;
    private Date reservationStop;
    private Date createdAt;
    private Date updatedAt;
    @Column(unique = true)
    private String reservationNumber;
    @ColumnDefault("false")
    private boolean paid;
    @ColumnDefault("false")
    private boolean cancelled;
}
