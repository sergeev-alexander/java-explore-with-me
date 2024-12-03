package alexander.sergeev.dto.event_dto;

import alexander.sergeev.model.Location;
import alexander.sergeev.validation.ValidationMarker;
import alexander.sergeev.validation.event_validation.NewEventDateTimeValidation;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.*;
import java.time.LocalDateTime;

import static alexander.sergeev.formatter.FormatterDateTime.DATE_TIME_PATTERN;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NewEventDto {

    @NotBlank(groups = ValidationMarker.OnCreate.class,
            message = "Creating event annotation field is blank!")
    @Size(min = 20, max = 2000, groups = ValidationMarker.OnCreate.class,
            message = "Creating event annotation field is smaller than 20 and is bigger than 2000 characters!")
    private String annotation;

    @NotNull(groups = ValidationMarker.OnCreate.class,
            message = "Creating event category field is null!")
    @Positive(groups = ValidationMarker.OnCreate.class,
            message = "Creating event category field must be positive!")
    private Long category;

    @NotBlank(groups = ValidationMarker.OnCreate.class,
            message = "Creating event description field is blank!")
    @Size(min = 20, max = 7000, groups = ValidationMarker.OnCreate.class,
            message = "Creating event description field is smaller than 20 or is bigger than 7000 characters!")
    private String description;

    @NotNull(groups = ValidationMarker.OnCreate.class,
            message = "Creating event event date field is blank!")
    @NewEventDateTimeValidation(groups = ValidationMarker.OnCreate.class)
    @JsonFormat(pattern = DATE_TIME_PATTERN)
    private LocalDateTime eventDate;

    @NotNull(groups = ValidationMarker.OnCreate.class,
            message = "Creating event location field is null!")
    private Location location;

    private Boolean paid;

    @PositiveOrZero(groups = ValidationMarker.OnCreate.class,
            message = "Creating event participant limit field must be positive!")
    private Long participantLimit;

    private Boolean requestModeration;

    @NotBlank(groups = ValidationMarker.OnCreate.class,
            message = "Creating event title field is blank!")
    @Size(min = 3, max = 120, groups = ValidationMarker.OnCreate.class,
            message = "Creating event title field is smaller than 3 or is bigger than 120 characters!")
    private String title;

}
