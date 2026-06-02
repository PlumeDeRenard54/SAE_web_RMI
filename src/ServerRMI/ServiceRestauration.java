package ServerRMI;

import donnees.Reservation;
import donnees.Restaurant;

import java.rmi.Remote;
import java.util.List;

public interface ServiceRestauration extends Remote {
    public void reservationRestaurant(Reservation reservation);
    public List<Restaurant> getRestaurants();
}
