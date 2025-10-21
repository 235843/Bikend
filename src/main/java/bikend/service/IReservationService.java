package bikend.service;

import bikend.domain.BikeEntity;
import bikend.domain.ReservationEntity;
import bikend.domain.UserEntity;
import bikend.utils.dtos.ReservationDTO;

import java.util.Date;
import java.util.List;

public interface IReservationService {
    List<ReservationEntity> getUsersReservation(UserEntity user);

    List<BikeEntity> getAvailableBikes(Date start, Date end);
    List<BikeEntity> getSpecificAvailableBikes(Date start, Date end, String model, String series);
    void createReservation(ReservationDTO reservationDTO, UserEntity user);
}
