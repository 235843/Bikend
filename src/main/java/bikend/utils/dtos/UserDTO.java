package bikend.utils.dtos;

import bikend.utils.enums.UserRole;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDTO {
    private String firstName;
    private String lastName;
    private String email;
    private String telephone;
    private UserRole role;
}
