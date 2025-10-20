package bikend.service;

import bikend.domain.BikeEntity;

import java.util.List;

public interface IBikeService {
    List<BikeEntity> getBikes();
    List<BikeEntity> getBikesByModel(String Model);
    List<BikeEntity> getAvailableModel(String Model);
    void addBike(BikeEntity bikeEntity);

}
