package cinema;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class Seat {
    private int row;
    private int column;
    @JsonIgnore
    private boolean isAvailable;
    private int price;

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public Seat(int row, int column) {
        this.row = row;
        this.column = column;
        this.isAvailable = true;
    }

    // Default constructor (needed for deserialization)
    public Seat() {
    }


    public int getRow() {
        return row;
    }

    public int getColumn() {
        return column;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public void setColumn(int column) {
        this.column = column;
    }

    @JsonIgnore
    public boolean isAvailable() {
        return isAvailable;
    }

    public void setAvailable(boolean available) {
        isAvailable = available;
    }
}
