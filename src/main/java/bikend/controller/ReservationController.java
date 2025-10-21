package bikend.controller;

import bikend.domain.BikeEntity;
import bikend.domain.UserEntity;
import bikend.service.IBikeService;
import bikend.service.IReservationService;
import bikend.service.IUserService;
import bikend.utils.Mapper;
import bikend.utils.dtos.BikeListDTO;
import bikend.utils.dtos.ReservationDTO;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/reservation")
public class ReservationController {
    private final IReservationService reservationService;
    private final IBikeService bikeService;
    private final IUserService userService;

    public ReservationController(IReservationService reservationService, IBikeService bikeService, IUserService userService) {
        this.reservationService = reservationService;
        this.bikeService = bikeService;
        this.userService = userService;
    }

    @GetMapping("/availableBikes")
    public ResponseEntity<BikeListDTO> getAvailableBikes(@RequestParam(name = "start") @DateTimeFormat(pattern = "dd.MM.yyyy") Date start,
                                                         @RequestParam(name = "end") @DateTimeFormat(pattern = "dd.MM.yyyy")Date end) {
        BikeListDTO bikes = new BikeListDTO();
        try {
            bikes = reservationService.getAvailableBikes(start, end);
        } catch (Exception e) {
            ResponseEntity.status(406).body("Błędne daty");
        }
        return ResponseEntity.ok(bikes);
    }

    @PostMapping("/create")
    public ResponseEntity<String> create(Authentication authentication, @RequestBody ReservationDTO reservation) {
        UserEntity user = userService.getUserByEmail(authentication.getName());
        if (user == null) {
            return ResponseEntity.status(401).body("Nie znaleziono użytkownika");
        }
        try {
            reservationService.createReservation(reservation, user);
        } catch (Exception e) {
            return ResponseEntity.status(500).body(e.getMessage());
        }

        return ResponseEntity.ok("Sukces!");
    }

}
