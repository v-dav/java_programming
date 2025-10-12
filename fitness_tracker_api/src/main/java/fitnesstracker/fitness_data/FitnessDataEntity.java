package fitnesstracker.fitness_data;

import fitnesstracker.applications.ApplicationEntity;
import fitnesstracker.common.BaseEntity;
import jakarta.persistence.*;

import java.time.Duration;


@Entity
@Table(name = "fitness_data")
public class FitnessDataEntity extends BaseEntity {

    private String username;
    private String activity;
    private Duration duration;
    private int calories;

    @ManyToOne
    @JoinColumn(name = "application_id")
    private ApplicationEntity application;

    public FitnessDataEntity() {
    }

    public FitnessDataEntity(String activity,
                             String username,
                             Duration duration,
                             int calories) {
        this.activity = activity;
        this.username = username;
        this.duration = duration;
        this.calories = calories;
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

    public Duration getDuration() {
        return duration;
    }

    public void setDuration(Duration duration) {
        this.duration = duration;
    }

    public int getCalories() {
        return calories;
    }

    public void setCalories(int calories) {
        this.calories = calories;
    }

    public ApplicationEntity getApplication() {
        return application;
    }

    public void setApplication(ApplicationEntity applicationEntity) {
        this.application = applicationEntity;
    }
}
