package bikend.service;

import bikend.domain.UserEntity;
import bikend.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService implements IUserService{

    private final UserRepository userRepository;
    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional
    public void addUser(UserEntity user) {
        userRepository.save(user);
    }

    @Transactional
    public void editUser(UserEntity user) {
        userRepository.save(user);
    }

    public UserEntity getUser(long id) {
        return userRepository.findById(id).orElse(null);
    }

    public UserEntity getUserByEmail(String email) {
        return userRepository.findByEmail(email).orElse(null);
    }

    public UserEntity getUserByToken(String token) {
        return userRepository.findByVerificationToken(token).orElse(null);
    }

    public List<UserEntity> getUsers() {
        return userRepository.findAll();
    }
}
