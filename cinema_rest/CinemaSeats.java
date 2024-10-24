package cinema;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Component
public class CinemaSeats {
    private final int rows;
    private final int columns;
    private List<Seat> seats;
    private List<Purchase> purchases;

    CinemaSeats() {
        this.rows = 9;
        this.columns = 9;
        this.seats = new ArrayList<>();
        createListSeats();
        purchases = new ArrayList<>();
    }

    private void createListSeats() {
        for(int i = 1; i <= 9; i++) {
            for(int j = 1; j <= 9; j++) {
                Seat seat = new Seat(i, j);
                seat.setPrice(getTicketPrice(i));
                this.seats.add(seat);
            }
        }
    }

    public static int getTicketPrice(int row) {
        if (row <= 4) {
            return 10;
        }
        return 8;
    }

    public int getRows() {
        return rows;
    }

    public int getColumns() {
        return columns;
    }

    public List<Seat> getSeats() {
        return seats;
    }

    public void setSeats(List<Seat> seats) {
        this.seats = seats;
    }

    public boolean isCinemaSeatAvailable(int row, int column) {
        for (Seat aSeat : seats) {
            if (aSeat.getRow() == row && aSeat.getColumn() == column) {
                return aSeat.isAvailable();
            }
        }
        return false;
    }

    public void setCinemaSeatAvailable(int row, int column, boolean status) {
        for (Seat aSeat : seats) {
            if (aSeat.getRow() == row && aSeat.getColumn() == column) {
                aSeat.setAvailable(status);
            }
        }
    }

    public List<Purchase> getPurchases() {
        return purchases;
    }

    public void setPurchases(List<Purchase> purchases) {
        this.purchases = purchases;
    }

    public void addPurchase(Purchase purchase) {
        purchases.add(purchase);
    }

    public void removePurchase(Purchase purchase) {
        purchases.removeIf(aPurchase -> aPurchase.equals(purchase));
    }
}
