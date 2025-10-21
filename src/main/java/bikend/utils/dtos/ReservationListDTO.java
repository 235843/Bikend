package bikend.utils.dtos;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class ReservationListDTO {
    private List<ReservationDTO> reservationList;
}
