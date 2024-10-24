package cinema;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.nio.file.AccessDeniedException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
public class SeatsController {
    private final SeatsService seatsService;

    public SeatsController(SeatsService seatsService, CinemaSeats cinemaSeats) {
        this.seatsService = seatsService;
    }

    @GetMapping("/seats")
    public ResponseEntity<Map<String, Object>> getSeats() {
        Map<String, Object> response = new HashMap<>();
        response.put("rows", seatsService.getAllSeats().getRows());
        response.put("columns", seatsService.getAllSeats().getColumns());
        response.put("seats", seatsService.getAvailableSeats());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/purchase")
    public ResponseEntity<Purchase> purchase(@RequestBody Seat seat) {
        return new ResponseEntity<>(seatsService.purchaseSeat(seat.getRow(), seat.getColumn()), HttpStatus.OK);
    }

    @PostMapping("/return")
    public ResponseEntity<Map<String, Ticket>> returnPurchase(@RequestBody Refund refund) {
        return new ResponseEntity<>(seatsService.refund(refund), HttpStatus.OK);
    }

    @GetMapping("/stats")
    public ResponseEntity<Stats> showStats(@RequestParam(name = "password", required = false) String password) throws AccessDeniedException {
        if(password == null || !"super_secret".equals(password)) {
            throw new AccessDeniedException("The password is wrong!");
        }
        return new ResponseEntity<>(seatsService.getStats(), HttpStatus.OK);
    }
}
