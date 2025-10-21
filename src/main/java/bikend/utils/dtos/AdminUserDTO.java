package bikend.utils.dtos;

import bikend.utils.enums.UserRole;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AdminUserDTO extends UserDTO{
    private UserRole role;
    private long id;
    private boolean blocked;
    private boolean activated;
}
