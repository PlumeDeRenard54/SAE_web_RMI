package ServerRMI;

import donnees.Reservation;
import donnees.Restaurant;

import java.io.Serializable;
import java.rmi.Remote;
import java.util.List;

public class ServeurRestauration implements ServiceRestauration {
    @Override
    public void reservationRestaurant(Reservation reservation) {

    }

    @Override
    public List<Restaurant> getRestaurants() {
        return List.of();
    }


}
