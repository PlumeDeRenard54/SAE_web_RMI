package donnees;

public class RestaurantActiveRecord {
    private Restaurant restaurant;

    public RestaurantActiveRecord(Restaurant restaurant){
        this.restaurant = restaurant;
    }

    public Restaurant getRestaurant() {
        return restaurant;
    }
}
