package interfaces;

import entities.Tour;

import java.util.Map;

public interface TourInterface {
        Map<Long, Tour> listTours();
        Tour findTourById(long id);

}
