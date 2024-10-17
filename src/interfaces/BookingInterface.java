package interfaces;

import entities.Booking;
import entities.Member;
import entities.Tour;

import java.util.Map;

public interface BookingInterface {
    Booking createBooking(Member member, Tour tour);
    Map<Long, Booking> listBookingsForTour(long tourId);
    Booking findBookingById(long id);
}
