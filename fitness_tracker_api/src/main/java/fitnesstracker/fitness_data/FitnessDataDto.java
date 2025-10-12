package fitnesstracker.fitness_data;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.Duration;
import java.util.UUID;

public class FitnessDataDto {

    private UUID id;
    private String username;
    private String activity;

    @JsonIgnore
    private Duration duration;
    private int calories;
    private String application;

    public FitnessDataDto(String username) {
        this.username = username;
    }

    public FitnessDataDto(UUID id, String username, String activity, Duration duration, int calories, String application) {
        this.id = id;
        this.username = username;
        this.activity = activity;
        this.duration = duration;
        this.calories = calories;
        this.application = application;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getActivity() {
        return activity;
    }

    public void setActivity(String activity) {
        this.activity = activity;
    }

    @JsonIgnore
    public Duration getDuration() {
        return duration;
    }

    public void setDuration(Duration duration) {
        this.duration = duration;
    }

    @JsonProperty("duration")
    public long getDurationInSeconds() {
        return duration != null ? duration.toSeconds() : 0;
    }

    public int getCalories() {
        return calories;
    }

    public void setCalories(int calories) {
        this.calories = calories;
    }

    public String getApplication() {
        return application;
    }

    public void setApplication(String application) {
        this.application = application;
    }
}
