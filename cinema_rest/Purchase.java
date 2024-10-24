package cinema;

import java.util.UUID;

public class Purchase {
    private Ticket ticket;
    private UUID token;

    public Purchase(Ticket ticket) {
        this.ticket = ticket;
        this.token = UUID.randomUUID();
    }

    public Purchase() {
    }

    public Ticket getTicket() {
        return ticket;
    }

    public void setTicket(Ticket ticket) {
        this.ticket = ticket;
    }

    public UUID getToken() {
        return token;
    }

    public void setToken(UUID token) {
        this.token = token;
    }
}
