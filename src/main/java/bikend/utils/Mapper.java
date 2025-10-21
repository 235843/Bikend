package bikend.utils;

import bikend.domain.BikeEntity;
import bikend.domain.ReservationEntity;
import bikend.domain.UserEntity;
import bikend.utils.dtos.AdminUserDTO;
import bikend.utils.dtos.BikeDTO;
import bikend.utils.dtos.ReservationDTO;
import bikend.utils.dtos.UserDTO;

import java.util.ArrayList;
import java.util.List;

public class Mapper {
    public static UserDTO userToDTO(UserEntity user) {
        UserDTO userDTO = new UserDTO();
        userDTO.setEmail(user.getEmail());
        userDTO.setLastName(user.getLastName());
        userDTO.setFirstName(user.getFirstName());
        userDTO.setTelephone(user.getTelephone());
        userDTO.setPassword("********");
        return userDTO;
    }

    public static AdminUserDTO adminUserDTO(UserEntity user) {
        AdminUserDTO adminUserDTO = new AdminUserDTO();
        adminUserDTO.setEmail(user.getEmail());
        adminUserDTO.setLastName(user.getLastName());
        adminUserDTO.setFirstName(user.getFirstName());
        adminUserDTO.setTelephone(user.getTelephone());
        adminUserDTO.setPassword("********");
        adminUserDTO.setId(user.getId());
        adminUserDTO.setActivated(user.isActivated());
        adminUserDTO.setBlocked(user.isBlocked());
        adminUserDTO.setRole(user.getRole());
        return adminUserDTO;
    }

    public static List<AdminUserDTO> adminUserDTOList(List<UserEntity> users) {
        List<AdminUserDTO> userDTOS = new ArrayList<>();
        for (UserEntity user: users) {
            userDTOS.add(adminUserDTO(user));
        }
        return userDTOS;
    }

    public static BikeDTO bikeToDTO(BikeEntity bike) {
        BikeDTO dto = new BikeDTO();
        dto.setCount(1);
        dto.setType(bike.getType());
        dto.setModel(bike.getModel());
        dto.setSeries(bike.getSeries());
        dto.setPricePerDay(bike.getPricePerDay());

        return dto;
    }

    public static List<BikeDTO> bikeDTOList(List<BikeEntity> bikes) {
        List<BikeDTO> dtos = new ArrayList<>();
        for (BikeEntity bike : bikes) {
            dtos.add(bikeToDTO(bike));
        }
        return dtos;
    }

    public static ReservationDTO reservationToDTO (ReservationEntity reservation) {
        ReservationDTO dto = new ReservationDTO();
        dto.setPaid(false);
        dto.setCancelled(false);
        dto.setCost(reservation.getCost());
        dto.setReservationStop(reservation.getReservationStop());
        dto.setReservationStart(reservation.getReservationStart());
        dto.setBikeList(bikeDTOList(reservation.getBikeList()));
        return dto;
    }

    public static List<ReservationDTO> reservationDTOList(List<ReservationEntity> reservations) {
        List<ReservationDTO> dtos = new ArrayList<>();
        for (ReservationEntity reservation : reservations) {
            dtos.add(reservationToDTO(reservation));
        }
        return dtos;
    }
}
