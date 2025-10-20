package bikend.controller;

import bikend.utils.jwt.JwtUtil;
import bikend.domain.UserEntity;
import bikend.service.IUserService;
import bikend.utils.mail.MailSender;
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
    public String register(@RequestBody UserEntity user) {
        user.setPassword(encoder.encode(user.getPassword()));
        try {
            String token = UUID.randomUUID().toString();
            user.setVerificationToken(token);
            userService.addUser(user);
            mailSender.sendWelcomeMail(user.getEmail(), user.getFirstName(), token);
        } catch (Exception e) {
            return "User exists!";
        }
        return "Registered!";
    }

    @PostMapping("/login")
    public Map<String, String> login(@RequestBody Map<String, String> data) {
        UserEntity user = userService.getUserByEmail(data.get("email"));
        if (user != null && encoder.matches(data.get("password"), user.getPassword())) {
            if(!user.isActivated()) {
                return Map.of("error", "Konto nie zostało aktywowane");
            }
            String token = JwtUtil.generateToken(user.getEmail(), user.getRole().name());
            return Map.of("token", token);
        } else {
            return Map.of("error", "Błędne dane logowania");
        }
    }

    @GetMapping("/verify")
    public String verifyToken(@RequestParam("token") String token) {
        UserEntity user = userService.getUserByToken(token);
        if (user == null) {
            return "Błędny token";
        }
        user.setActivated(true);
        userService.editUser(user);
        return "Sukces!";
    }

}
