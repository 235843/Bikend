package bikend.domain;

import bikend.utils.enums.BikeType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "bike")
public class BikeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String model;
    private String series;
    @Enumerated(EnumType.STRING)
    private BikeType type;
    private double pricePerDay;
    @Column(unique = true)
    private String uniqueCode;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        return uniqueCode.equals(((BikeEntity) o).getUniqueCode());
    }
}
