package donnees;

public class ReservationActiveRecord {
    private Reservation reservation;

    public ReservationActiveRecord(Reservation reservation){
        this.reservation = reservation;
    }

    public Reservation getReservation() {
        return reservation;
    }
}
