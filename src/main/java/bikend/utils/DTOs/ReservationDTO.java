package bikend.utils.DTOs;

import bikend.domain.BikeEntity;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter
@Setter
public class ReservationDTO {
    private List<BikeDTO> bikeList;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss", timezone = "Europe/Warsaw")
    private Date reservationStart;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss", timezone = "Europe/Warsaw")
    private Date reservationStop;
    private boolean paid;
    private boolean cancelled;
    private double cost;
}
