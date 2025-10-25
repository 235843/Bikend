package bikend.controller;

import bikend.configuration.security.service.JwtBlacklistService;
import bikend.domain.ReservationEntity;
import bikend.domain.UserEntity;
import bikend.service.IReservationService;
import bikend.service.IUserService;
import bikend.utils.Mapper;
import bikend.utils.dtos.LoginDTO;
import bikend.utils.dtos.ReservationListDTO;
import bikend.utils.dtos.UserDTO;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping(value = "/user")
@RestController
public class UserController {
    private final IUserService userService;
    private final IReservationService reservationService;
    private final JwtBlacklistService jwtBlacklistService;
    private final PasswordEncoder encoder;
    public UserController (IUserService userService, IReservationService reservationService,
                           JwtBlacklistService jwtBlacklistService, PasswordEncoder encoder) {
        this.userService = userService;
        this.reservationService = reservationService;
        this.jwtBlacklistService = jwtBlacklistService;
        this.encoder = encoder;
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

    @PatchMapping(value = "/changePassword")
    public ResponseEntity<UserDTO> changePassword(Authentication authentication, @RequestBody LoginDTO loginDTO) {
        UserEntity user = userService.getUserByEmail(authentication.getName());
        user.setPassword(encoder.encode(loginDTO.getPassword()));
        userService.editUser(user);
        return ResponseEntity.ok(Mapper.userToDTO(user));
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
}

