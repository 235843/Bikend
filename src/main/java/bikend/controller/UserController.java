package bikend.controller;

import bikend.domain.ReservationEntity;
import bikend.domain.UserEntity;
import bikend.service.IReservationService;
import bikend.service.IUserService;
import bikend.utils.DTOs.ReservationDTO;
import bikend.utils.Mapper;
import bikend.utils.DTOs.UserDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping(value = "/user")
@RestController
public class UserController {
    private final IUserService userService;
    private final IReservationService reservationService;
    public UserController (IUserService userService, IReservationService reservationService) {
        this.userService = userService;
        this.reservationService = reservationService;
    }

    @GetMapping(value = "/userInfo")
    public ResponseEntity<UserDTO> showUserInfo(Authentication authentication) {
        return ResponseEntity.ok(Mapper.userToDTO(userService.getUserByEmail(authentication.getName())));
    }

    @PostMapping(value = "/editUser")
    public ResponseEntity<UserDTO> editUser(Authentication authentication,@RequestBody UserDTO userDTO) {
        UserEntity user = userService.getUserByEmail(authentication.getName());
        user.setEmail(userDTO.getEmail());
        user.setFirstName(userDTO.getFirstName());
        user.setLastName(userDTO.getLastName());
        user.setTelephone(userDTO.getTelephone());
        userService.editUser(user);
        return ResponseEntity.ok(userDTO);
    }

    @GetMapping(value = "/reservations")
    public ResponseEntity<List<ReservationDTO>> getReservations(Authentication authentication) {
        List<ReservationEntity> reservations = reservationService.getUsersReservation(userService.getUserByEmail(authentication.getName()));
        return ResponseEntity.ok(Mapper.reservationDTOList(reservations));
    }

}

