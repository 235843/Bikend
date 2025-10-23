package bikend.utils.dtos;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class AdminUserListDTO {
    private List<AdminUserDTO> userList;
}
