package bikend.service;

import bikend.domain.UserEntity;

import java.util.List;

public interface IUserService {
     void addUser(UserEntity user);
     void editUser(UserEntity user);
     UserEntity getUser(long id);
     List<UserEntity> getUsers();
     UserEntity getUserByEmail(String email);
     UserEntity getUserByToken(String token);

}
