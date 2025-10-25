package bikend.controller;

import bikend.utils.dtos.LoginDTO;
import bikend.utils.jwt.JwtUtil;
import bikend.domain.UserEntity;
import bikend.service.IUserService;
import bikend.utils.mail.MailSender;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final IUserService userService;
    private final PasswordEncoder encoder;
    private final MailSender mailSender;

    public AuthController(IUserService userService, PasswordEncoder passwordEncoder, MailSender mailSender) {
        this.userService = userService;
        this.encoder = passwordEncoder;
        this.mailSender = mailSender;
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody UserEntity user) {
        user.setPassword(encoder.encode(user.getPassword()));
        String token = UUID.randomUUID().toString();
        user.setVerificationToken(token);
        try {
            userService.addUser(user);
        } catch (Exception e) {
            return ResponseEntity.status(406).body("Użytkownik istnieje");
        }
        mailSender.sendWelcomeMail(user.getEmail(), user.getFirstName(), token);
        return ResponseEntity.ok("Użytkownik zarejestrowany");
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginDTO loginDTO) {
        UserEntity user = userService.getUserByEmail(loginDTO.getEmail());
        if (user != null && encoder.matches(loginDTO.getPassword(), user.getPassword())) {
            if(!user.isActivated()) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Konto nie zostało aktywowane");
            }
            String token = JwtUtil.generateToken(user.getEmail(), user.getRole().name());
            return ResponseEntity.ok(token);
        } else {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body( "Błędne dane logowania");
        }
    }

    @GetMapping("/verify")
    public ResponseEntity<String> verifyToken(@RequestParam("token") String token) {
        UserEntity user = userService.getUserByToken(token);
        if (user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Błędny token");
        }
        user.setActivated(true);
        userService.editUser(user);
        return ResponseEntity.ok("Sukces");
    }

}
