package traffic;

public class Road {
    private String state;
    private int counter = 0;
    private String name;

    public Road(String name) {
        this.name = name;
        this.counter = 0;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getState() {
        return state;
    }
    public void setState(String state) {
        this.state = state;
    }

    public int getCounter() {
        return counter;
    }

    public void setCounter(int counter) {
        this.counter = counter;
    }
}
