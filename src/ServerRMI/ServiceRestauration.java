package ServerRMI;

import donnees.Reservation;
import donnees.Restaurant;
import java.util.List;

public interface ServiceRestauration {
    public void reservationRestaurant(Reservation reservation);
    public List<Restaurant> getRestaurants();
}
