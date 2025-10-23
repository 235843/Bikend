package bikend.controller;

import bikend.domain.BikeEntity;
import bikend.service.IBikeService;
import bikend.service.IUserService;
import bikend.utils.CodeGenerator;
import bikend.utils.Mapper;
import bikend.utils.dtos.AdminUserDTO;
import bikend.utils.dtos.AdminUserListDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/admin")
@RestController
public class AdminController {
    private final IUserService userService;
    private final IBikeService bikeService;

    public AdminController(IUserService userService, IBikeService bikeService) {
        this.userService = userService;
        this.bikeService = bikeService;
    }

    @GetMapping("/users")
    public ResponseEntity<AdminUserListDTO> getUsers() {
        return ResponseEntity.ok(Mapper.adminUserDTOList(userService.getUsers()));
    }

    @PostMapping("/addBikes")
    public ResponseEntity<String> addBikes(@RequestBody List<BikeEntity> bikes) {
        for (BikeEntity bike : bikes) {
            bikeService.addBike(bike);
        }
        return ResponseEntity.ok("Sukces!");
    }
}
