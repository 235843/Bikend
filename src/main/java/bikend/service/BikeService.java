package bikend.service;

import bikend.domain.BikeEntity;
import bikend.repository.BikeRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BikeService implements IBikeService{
    private final BikeRepository bikeRepository;

    public BikeService(BikeRepository bikeRepository) {
        this.bikeRepository = bikeRepository;
    }

    @Override
    public List<BikeEntity> getBikes() {
        return bikeRepository.findAll();
    }

    @Override
    public List<BikeEntity> getBikesByModel(String Model) {
        return null;
    }

    @Override
    public List<BikeEntity> getAvailableModel(String Model) {
        return null;
    }

    @Override
    public void addBike(BikeEntity bikeEntity) {
        bikeRepository.save(bikeEntity);
    }
}
