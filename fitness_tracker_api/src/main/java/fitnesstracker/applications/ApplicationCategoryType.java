package fitnesstracker.applications;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum ApplicationCategoryType {

    @JsonProperty("basic")
    BASIC("basic"),

    @JsonProperty("premium")
    PREMIUM("premium");

    private final String value;

    ApplicationCategoryType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
