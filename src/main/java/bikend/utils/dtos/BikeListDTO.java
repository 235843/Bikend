package bikend.utils.dtos;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class BikeListDTO {
    private List<BikeDTO> bikeDTOList;
}
