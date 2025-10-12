package fitnesstracker.fitness_data;

import java.time.Duration;

public record UploadDataRequest (
        String username,
        String activity,
        Duration duration,
        int calories) {}
