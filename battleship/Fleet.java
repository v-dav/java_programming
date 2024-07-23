package battleship;

public class Fleet {
    private Ship aircraftCarrier;
    private Ship battleShip;
    private Ship submarine;
    private Ship cruiser;
    private Ship destroyer;

    private boolean alive;
    private int numShips;

    public Fleet(Ship aircraftCarrier, Ship battleShip, Ship submarine, Ship cruiser, Ship destroyer) {
        this.aircraftCarrier = aircraftCarrier;
        this.battleShip = battleShip;
        this.submarine = submarine;
        this.cruiser = cruiser;
        this.destroyer = destroyer;
        this.alive = true;
        this.numShips = 5;
    }

    private Ship[] getShipsList() {
        return new Ship[] {
                aircraftCarrier,
                battleShip,
                submarine,
                cruiser,
                destroyer
        };
    }

    public Ship hitShip(String y, int x) {
        String coordinates = y + String.valueOf(x);
        Ship hitShip = null;

        // Determine hitten ship
        for (Ship ship : getShipsList()) {
            for (String coordinate : ship.getParts()) {
                if (coordinate.equals(coordinates)) {
                    hitShip = ship;
                    break;
                }
            }
        }

        assert hitShip != null;

        if (hitShip.getHealth() > 0) {
            hitShip.diminishHealth();
            if (hitShip.getHealth() == 0) {
                numShips--;
                hitShip.setAlive(false);
            }
        }
        return hitShip;
    }

    public boolean isAlive() {
        if (numShips == 0) {
            alive = false;
        }
        return alive;
    }

}
