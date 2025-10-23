package bikend.utils;

import bikend.domain.BikeEntity;
import bikend.domain.ReservationEntity;
import bikend.domain.UserEntity;
import bikend.utils.dtos.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Mapper {
    public static UserDTO userToDTO(UserEntity user) {
        UserDTO userDTO = new UserDTO();
        userDTO.setEmail(user.getEmail());
        userDTO.setLastName(user.getLastName());
        userDTO.setFirstName(user.getFirstName());
        userDTO.setTelephone(user.getTelephone());
        return userDTO;
    }

    public static AdminUserDTO adminUserDTO(UserEntity user) {
        AdminUserDTO adminUserDTO = new AdminUserDTO();
        adminUserDTO.setEmail(user.getEmail());
        adminUserDTO.setLastName(user.getLastName());
        adminUserDTO.setFirstName(user.getFirstName());
        adminUserDTO.setTelephone(user.getTelephone());
        adminUserDTO.setId(user.getId());
        adminUserDTO.setActivated(user.isActivated());
        adminUserDTO.setBlocked(user.isBlocked());
        adminUserDTO.setRole(user.getRole());
        return adminUserDTO;
    }

    public static AdminUserListDTO adminUserDTOList(List<UserEntity> users) {
        AdminUserListDTO adminUserListDTO = new AdminUserListDTO();
        List<AdminUserDTO> userDTOS = new ArrayList<>();
        for (UserEntity user: users) {
            userDTOS.add(adminUserDTO(user));
        }
        adminUserListDTO.setUserList(userDTOS);
        return adminUserListDTO;
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

    public static BikeListDTO bikeDTOList(List<BikeEntity> bikes) {
        Map<String, Long> counts = bikes.stream()
                .collect(Collectors.groupingBy(
                        bike -> bike.getModel() + "|" + bike.getSeries(),
                        Collectors.counting()
                ));

        List<BikeDTO> dtos = bikes.stream()
                .map(bike -> {
                    BikeDTO dto = bikeToDTO(bike);
                    String key = bike.getModel() + "|" + bike.getSeries();
                    dto.setCount(counts.getOrDefault(key, 1L).intValue());
                    return dto;
                })
                .distinct()
                .collect(Collectors.toList());

        BikeListDTO bikeListDTO = new BikeListDTO();
        bikeListDTO.setBikeDTOList(dtos);
        return bikeListDTO;
    }

    public static ReservationDTO reservationToDTO (ReservationEntity reservation) {
        ReservationDTO dto = new ReservationDTO();
        dto.setPaid(false);
        dto.setCancelled(false);
        dto.setCost(reservation.getCost());
        dto.setReservationStop(reservation.getReservationStop());
        dto.setReservationStart(reservation.getReservationStart());
        dto.setBikeDTOList(bikeDTOList(reservation.getBikeList()).getBikeDTOList());
        dto.setReservationNumber(reservation.getReservationNumber());
        return dto;
    }

    public static ReservationListDTO reservationDTOList(List<ReservationEntity> reservations) {
        ReservationListDTO reservationListDTO = new ReservationListDTO();
        List<ReservationDTO> dtos = new ArrayList<>();
        for (ReservationEntity reservation : reservations) {
            dtos.add(reservationToDTO(reservation));
        }
        reservationListDTO.setReservationList(dtos);
        return reservationListDTO;
    }
}
