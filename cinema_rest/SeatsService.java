package cinema;

import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class SeatsService {
    private final CinemaSeats cinemaSeats;

    public SeatsService(CinemaSeats cinemaSeats) {
        this.cinemaSeats = cinemaSeats;
    }

    public Purchase purchaseSeat(int row, int column) {
        if (row < 1 || column < 1 || row > cinemaSeats.getRows() || column > cinemaSeats.getColumns()) {
            throw new SeatOutOfBoundsException("The number of a row or a column is out of bounds!");
        }

        if (!cinemaSeats.isCinemaSeatAvailable(row, column)) {
            throw new SeatAlreadyPurchasedException("The ticket has been already purchased!");
        }
        Ticket ticket = new Ticket(row, column, CinemaSeats.getTicketPrice(row));
        Purchase purchase = new Purchase(ticket);
        cinemaSeats.setCinemaSeatAvailable(row, column, false);
        cinemaSeats.addPurchase(purchase);
        return purchase;
    }

    public CinemaSeats getAllSeats() {
        return cinemaSeats;
    }

    public List<Seat> getAvailableSeats() {
        return cinemaSeats
                .getSeats()
                .stream()
                .filter(Seat::isAvailable)
                .collect(Collectors.toList());
    }

    public Map<String, Ticket> refund(Refund refund) {
        UUID token = refund.token();
        Purchase purchaseToRemove = cinemaSeats
                .getPurchases()
                .stream()
                .filter(purchase -> purchase.getToken().equals(token))
                .findFirst()
                .orElseThrow(() -> new PurchaseNotFoundException("Wrong token!"));

        cinemaSeats.removePurchase(purchaseToRemove);
        cinemaSeats.setCinemaSeatAvailable(purchaseToRemove.getTicket().getRow(),
                purchaseToRemove.getTicket().getColumn(), true);

        HashMap<String, Ticket> response = new HashMap<>();
        response.put("ticket", purchaseToRemove.getTicket());
        return response;
    }

    public Stats getStats() {
        int calculatedIncome;
        int calculatedAvailable;
        int calculatedPurchased;
        Stats stats = new Stats();
        List<Purchase> cinemaPurchases = cinemaSeats.getPurchases();

        if (cinemaPurchases.isEmpty()) {
            calculatedIncome = 0;
            calculatedAvailable = cinemaSeats.getSeats().size();
            calculatedPurchased = 0;
        } else {
            calculatedIncome = cinemaPurchases.stream()
                    .mapToInt(purchase -> purchase.getTicket().getPrice())
                    .sum();

            calculatedPurchased = cinemaPurchases.size();
            calculatedAvailable = (int) cinemaSeats.getSeats().stream()
                    .filter(Seat::isAvailable)
                    .count();
        }

        stats.setIncome(calculatedIncome);
        stats.setAvailable(calculatedAvailable);
        stats.setPurchased(calculatedPurchased);

        return stats;
    }
}
