package bikend.repository;

import bikend.domain.BikeEntity;
import bikend.domain.ReservationEntity;
import bikend.domain.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface ReservationRepository extends JpaRepository<ReservationEntity, Long> {
    Optional<List<ReservationEntity>> getAllByUser(UserEntity user);
    @Query("SELECT r FROM ReservationEntity r " +
            "WHERE r.reservationStart <= :endDate " +
            "AND r.reservationStop >= :startDate")
    Optional<List<ReservationEntity>> findConflictsReservations(
            @Param("startDate") Date startDate,
            @Param("endDate") Date endDate
    );

    @Modifying
    @Query("UPDATE ReservationEntity r " +
            "SET r.cancelled = true " +
            "WHERE r.reservationStart >= :from " +
            "AND r.reservationStart <= :to " +
            "AND r.cancelled = false " +
            "AND r.paid = false ")
    int cancelReservations(
            @Param("from") Date from,
            @Param("to") Date to
    );

}
