package bikend.repository;

import bikend.domain.BikeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BikeRepository extends JpaRepository<BikeEntity, Long> {
    Optional<List<BikeEntity>> findAllByModelAndSeries(String bikeModel, String bikeSeries);
}
