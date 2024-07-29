package traffic;

public class SystemState extends Thread {
    private int secondsSinceStartUp = 0;
    private final int roads;
    private final int intervals;
    private boolean running;
    private boolean systemState;
    public CircularQueue roadsQueue;

    SystemState(int roads, int intervals) {
        this.roads = roads;
        this.intervals = intervals;
        this.running = false;
        this.systemState = false;
        this.roadsQueue = new CircularQueue(roads, intervals);
    }

    @Override
    public void run() {
        running = true;
        while (running) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                running = false;
            }
            secondsSinceStartUp++;

            if (secondsSinceStartUp > 1) {
                roadsQueue.updateCountersAndState();
            }

            if (systemState) {
                printState();
            }

        }
    }

    public void stopRunning() {
        running = false;
    }

    public int getRoads() {
        return roads;
    }

    public int getIntervals() {
        return intervals;
    }

    public boolean isRunning() {
        return running;
    }

    public int getSecondsSinceStartUp() {
        return secondsSinceStartUp;
    }

    public void setSystemState(boolean systemState) {
        this.systemState = systemState;
    }

    public void printState() {
        Main.clearConsole();
        System.out.printf(
                """
                        ! %ds. have passed since system startup !
                        ! Number of roads: %d !
                        ! Interval: %d !
                        \n""",
                secondsSinceStartUp,
                roads,
                intervals);
        if (!roadsQueue.isEmpty()) {
            roadsQueue.printQueueContent();
            System.out.println();
        }
        System.out.println("! Press \"Enter\" to open menu !");
    }
}

class CircularQueue {
    private int front, rear;
    private final Road[] roads;
    private final int interval;

    CircularQueue(int capacity, int interval) {
        this.front = this.rear = -1;
        this.roads = new Road[capacity];
        this.interval = interval;
    }

    public void enqueue(Road road) {
        if (isFull()) {
            System.out.println("Queue is full");
            return;
        } else if (isEmpty()) {
            front++;
            road.setState("open");
        } else {
            road.setState("closed");
        }
        road.setCounter(interval);
        rear = (rear + 1) % roads.length;
        roads[rear] = road;
        if (rear != 0 && roads[rear - 1].getState().equals("closed")) {
            road.setCounter(interval + roads[rear - 1].getCounter());
        }
        System.out.println(road.getName() + " Added!");
    }

    public void dequeue() {
        if (isEmpty()) {
            System.out.println("Queue is empty");
            return;
        }
        Road temp = roads[front];
        if (front == rear) {
            front = rear = -1;
        } else {
            front = (front + 1) % roads.length;
        }
        System.out.println(temp.getName() + " deleted!");
    }

    public boolean isEmpty() {
        return front == -1;
    }

    public boolean isFull() {
        return (rear + 1) % roads.length == front;
    }

    public void printQueueContent() {
        String ANSI_RESET = "\u001B[0m";
        for (int i = front; i <= rear; i++) {
            String roadName = roads[i].getName();
            String roadState = roads[i].getState();
            String colorModifier = roadState.equals("open") ? "\u001B[32m" : "\u001B[31m";
            int roadCounter = roads[i].getCounter();

            System.out.printf("%s will be %s%s for %ds.%s\n", roadName, colorModifier, roadState, roadCounter, ANSI_RESET);
        }
        System.out.println();
    }


    public void updateCountersAndState() {
        int roadsInQueue = rear - front + 1;
        // Empty queue
        if (this.isEmpty()) {
            return;
        } else if (roadsInQueue == 1) { // Only one element in the queue
            Road onlyRoad = roads[front];
            onlyRoad.setState("open");
            int onlyRoadCounter = onlyRoad.getCounter();
            if (onlyRoadCounter > 1) {
                onlyRoad.setCounter(onlyRoadCounter - 1);
            } else {
                onlyRoad.setCounter(interval);
            }
        } else { // Multiple elements in the queue, looping and updating each element


            for (int i = front; i <= rear; i++) {
                Road road = roads[i];
                String roadState = road.getState();
                int roadCounter = road.getCounter();

                if (roadState.equals("open")) { // if road is in open state
                    if (roadCounter > 1) {
                        road.setCounter(roadCounter - 1);
                    } else {
                        road.setState("closed");
                        road.setCounter(interval * (roadsInQueue - 1));
                    }
                } else { // if road is in closed state
                    if (i == front) { // and first in the queue
                        if (roadCounter > 1) {
                            road.setCounter(roadCounter - 1);
                        } else {
                            road.setState("open");
                            road.setCounter(interval);
                        }
                    } else { // next or last in queue. Roads are closed by default  on enqueuing
                        Road precedingRoad = roads[i - 1];
                        String precedingRoadState = precedingRoad.getState();
                        int precedingRoadCounter = precedingRoad.getCounter();

                        if (precedingRoadState.equals("open")) {
                            road.setCounter(precedingRoadCounter);
                            road.setState("closed");
                        } else {
                            if (roadCounter > 1) {
                                road.setCounter(roadCounter - 1);
                            } else {
                                road.setState("open");
                                road.setCounter(interval);
                            }
                        }
                    }
                }
            }
        }
    }
}
