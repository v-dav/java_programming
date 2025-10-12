package fitnesstracker.applications;

import java.util.UUID;

public record ApplicationDto(UUID id,
                             String name,
                             String description,
                             String apikey,
                             ApplicationCategoryType category)
{}