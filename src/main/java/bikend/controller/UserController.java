package bikend.controller;

import bikend.configuration.security.service.JwtBlacklistService;
import bikend.domain.ReservationEntity;
import bikend.domain.UserEntity;
import bikend.service.IReservationService;
import bikend.service.IUserService;
import bikend.utils.Mapper;
import bikend.utils.dtos.ReservationListDTO;
import bikend.utils.dtos.UserDTO;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping(value = "/user")
@RestController
public class UserController {
    private final IUserService userService;
    private final IReservationService reservationService;
    private final JwtBlacklistService jwtBlacklistService;
    public UserController (IUserService userService, IReservationService reservationService, JwtBlacklistService jwtBlacklistService) {
        this.userService = userService;
        this.reservationService = reservationService;
        this.jwtBlacklistService = jwtBlacklistService;
    }

    @GetMapping(value = "/userInfo")
    public ResponseEntity<UserDTO> showUserInfo(Authentication authentication) {
        return ResponseEntity.ok(Mapper.userToDTO(userService.getUserByEmail(authentication.getName())));
    }

    @PutMapping(value = "/editUser")
    public ResponseEntity<UserDTO> editUser(Authentication authentication, @RequestBody UserDTO userDTO) {
        UserEntity user = userService.getUserByEmail(authentication.getName());
        user.setEmail(userDTO.getEmail());
        user.setFirstName(userDTO.getFirstName());
        user.setLastName(userDTO.getLastName());
        user.setTelephone(userDTO.getTelephone());
        userService.editUser(user);
        return ResponseEntity.ok(userDTO);
    }

    @GetMapping(value = "/reservations")
    public ResponseEntity<ReservationListDTO> getReservations(Authentication authentication) {
        List<ReservationEntity> reservations = reservationService.getUsersReservation(userService.getUserByEmail(authentication.getName()));
        return ResponseEntity.ok(Mapper.reservationDTOList(reservations));
    }
    @GetMapping("/logout")
    public ResponseEntity<String> logout(HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Brak tokena");
        }

        String token = authHeader.substring(7);
        jwtBlacklistService.revokeToken(token);

        return ResponseEntity.ok("Wylogowano");
    }

    @PatchMapping(value = "/pay")
    public ResponseEntity<String> payForReservation(Authentication authentication,
                                                    @RequestParam(name = "reservationNumber") String reservationNumber) {
        UserEntity user = userService.getUserByEmail(authentication.getName());
        reservationService.payForReservation(user, reservationNumber);
        return ResponseEntity.ok("Opłacono rezerwację");
    }

    @PatchMapping(value = "/cancel")
    public ResponseEntity<String> cancelReservation(Authentication authentication,
                                                    @RequestParam(name = "reservationNumber") String reservationNumber) {
        UserEntity user = userService.getUserByEmail(authentication.getName());
        reservationService.cancelReservation(user, reservationNumber);
        return ResponseEntity.ok("Anulowano rezerwację");
    }

}

