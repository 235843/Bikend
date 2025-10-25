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
import org.springframework.http.HttpStatus;
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
        if (start.getTime() > end.getTime()) {
            ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Wprowadzono niepoprawne daty");
        }

        BikeListDTO bikes = reservationService.getAvailableBikes(start, end);
        return ResponseEntity.ok(bikes);
    }

    @PostMapping("/create")
    public ResponseEntity<String> create(Authentication authentication, @RequestBody ReservationDTO reservation) {
        UserEntity user = userService.getUserByEmail(authentication.getName());
        if (reservationService.createReservation(reservation, user) == 1) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Nie można dokonać rezerwacji");
        }
        return ResponseEntity.ok("Sukces!");
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
