package bikend.utils.DTOs;

import bikend.utils.enums.BikeType;
import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

@Getter
@Setter
public class BikeDTO {
    private String model;
    private String series;
    private int count;
    private BikeType type;
    private double pricePerDay;
}
