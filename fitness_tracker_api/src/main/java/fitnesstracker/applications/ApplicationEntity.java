package fitnesstracker.applications;

import fitnesstracker.common.BaseEntity;
import fitnesstracker.users.UserEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "applications")
public class ApplicationEntity extends BaseEntity {

    @NotBlank
    @Column(unique = true)
    private String name;

    @NotNull
    private String description;

    @NotBlank
    @Column(unique = true)
    private String apikey;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity user;

    @Enumerated(EnumType.STRING)
    private ApplicationCategoryType category;

    public ApplicationEntity() {
    }

    public ApplicationEntity(String name,
                             String description,
                             String apikey,
                             UserEntity user,
                             ApplicationCategoryType category) {
        this.name = name;
        this.description = description;
        this.apikey = apikey;
        this.user = user;
        this.category = category;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getApikey() {
        return apikey;
    }

    public void setApikey(String apikey) {
        this.apikey = apikey;
    }

    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity userEntity) {
        this.user = userEntity;
    }

    public ApplicationCategoryType getCategory() {
        return category;
    }

    public void setCategory(ApplicationCategoryType category) {
        this.category = category;
    }
}