package bikend.utils.dtos;

import bikend.utils.enums.BikeType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BikeDTO {
    private String model;
    private String series;
    private int count;
    private BikeType type;
    private double pricePerDay;
}
