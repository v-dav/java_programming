package fitnesstracker.applications;

public record ApplicationRegistrationResponse(String name, String apikey, ApplicationCategoryType category) {}