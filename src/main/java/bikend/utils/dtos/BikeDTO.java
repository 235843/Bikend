package bikend.utils.dtos;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (this.getClass() != o.getClass()) {
            return false;
        }
        return model.equals(((BikeDTO) o).model) &&
                series.equals(((BikeDTO) o).series);
    }

    @Override
    public int hashCode() {
        return Objects.hash(model, series);
    }
}
