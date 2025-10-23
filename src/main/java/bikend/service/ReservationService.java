package bikend.service;

import bikend.domain.BikeEntity;
import bikend.domain.ReservationEntity;
import bikend.domain.UserEntity;
import bikend.repository.BikeRepository;
import bikend.repository.ReservationRepository;
import bikend.utils.CodeGenerator;
import bikend.utils.DateHelper;
import bikend.utils.Mapper;
import bikend.utils.dtos.BikeDTO;
import bikend.utils.dtos.BikeListDTO;
import bikend.utils.dtos.ReservationDTO;
import jakarta.annotation.PostConstruct;
import jakarta.transaction.Transactional;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
public class ReservationService implements IReservationService {
    private final ReservationRepository reservationRepository;
    private final BikeRepository bikeRepository;

    public ReservationService(ReservationRepository reservationRepository, BikeRepository bikeRepository) {
        this.reservationRepository = reservationRepository;
        this.bikeRepository = bikeRepository;
    }

    @PostConstruct
    public void post() {
        System.out.println("Dzień dobry");
    }
    @Override
    public List<ReservationEntity> getUsersReservation(UserEntity user) {
        return reservationRepository.getAllByUser(user).orElse(new ArrayList<>());
    }



    @Transactional
    @Override
    public int createReservation(ReservationDTO reservationDTO, UserEntity user) {
        ReservationEntity reservation = new ReservationEntity();
        reservation.setUser(user);
        reservation.setReservationStart(reservationDTO.getReservationStart());
        reservation.setReservationStop(reservationDTO.getReservationStop());
        reservation.setReservationNumber(CodeGenerator.generateCode(24));

        List<BikeEntity> bikes = new ArrayList<>();
        double cost = 0;
        long days = TimeUnit.DAYS.convert(reservation.getReservationStop().getTime()
                - reservation.getReservationStart().getTime(), TimeUnit.MILLISECONDS) + 1;

        for (BikeDTO bike : reservationDTO.getBikeDTOList()) {
            cost += bike.getPricePerDay() * days * bike.getCount();
            List<BikeEntity> availableBikes = getSpecificAvailableBikes(reservation.getReservationStart(),
                    reservation.getReservationStop(), bike.getModel(), bike.getSeries());
            if (availableBikes.size() < bike.getCount()) {
                return 1;
            }
            bikes.addAll(availableBikes.subList(0, bike.getCount()));
        }
        reservation.setCost(cost);
        reservation.setBikeList(bikes);

        reservationRepository.save(reservation);
        return 0;
    }

    @Override
    public BikeListDTO getAvailableBikes(Date start, Date end) {
        List<BikeEntity> allBikes = bikeRepository.findAll();
        List<ReservationEntity> reservations = reservationRepository.findConflictsReservations(start, end).orElse(new ArrayList<>());
        for (ReservationEntity reservation : reservations) {
            for (BikeEntity bike : reservation.getBikeList()) {
                allBikes.remove(bike);
            }
        }
        return Mapper.bikeDTOList(allBikes);
    }

    @Override
    public List<BikeEntity> getSpecificAvailableBikes(Date start, Date end, String model, String series) {
        List<BikeEntity> allBikes = bikeRepository.
                findAllByModelAndSeries(model, series).orElse(new ArrayList<>());
        List<ReservationEntity> reservations = reservationRepository.
                findConflictsReservations(start, end).orElse(new ArrayList<>());

        for (ReservationEntity reservation : reservations) {
            for (BikeEntity bike : reservation.getBikeList()) {
                if (model.equals(bike.getModel()) && series.equals(bike.getSeries())) {
                    allBikes.remove(bike);
                }
            }
        }
        return allBikes;
    }

    @Transactional
    @Scheduled(cron = "0 50 22 * * ?", zone = "Europe/Warsaw")
    public void cancelUnpaidReservations() {
        System.out.println("Schedule task start");
        Date from = DateHelper.createDate(0);
        Date to = DateHelper.createDate(2);
        reservationRepository.cancelUnpaidReservations(from, to);
        System.out.println("Schedule task completed");
    }

    @Transactional
    @Override
    public void cancelReservation(UserEntity user, String reservationCode) {
        reservationRepository.cancelReservation(reservationCode, user);
    }

    @Transactional
    @Override
    public void payForReservation(UserEntity user, String reservationCode) {
        reservationRepository.payForReservation(reservationCode, user);
    }

}
