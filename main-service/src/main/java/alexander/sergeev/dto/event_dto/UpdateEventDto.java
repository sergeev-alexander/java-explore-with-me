package alexander.sergeev.dto.event_dto;

import alexander.sergeev.model.Location;
import alexander.sergeev.validation.ValidationMarker;
import alexander.sergeev.validation.event_validation.*;
import alexander.sergeev.validation.user_state_action_validation.UpdateByUserStateActionValidation;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Positive;
import java.time.LocalDateTime;

import static alexander.sergeev.formatter.FormatterDateTime.DATE_TIME_PATTERN;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateEventDto {

    @UpdateEventAnnotationValidation(groups = {ValidationMarker.OnUpdate.class, ValidationMarker.OnAdminUpdate.class})
    private String annotation;

    @Positive(groups = {ValidationMarker.OnUpdate.class, ValidationMarker.OnAdminUpdate.class},
            message = "Updating event category field must be positive!")
    private Long category;

    @UpdateEventDescriptionValidation(groups = {ValidationMarker.OnUpdate.class, ValidationMarker.OnAdminUpdate.class})
    private String description;

    @UpdateEventDateTimeValidation(groups = ValidationMarker.OnUpdate.class)
    @UpdateEventByAdminDateTimeValidation(groups = ValidationMarker.OnAdminUpdate.class)
    @JsonFormat(pattern = DATE_TIME_PATTERN)
    private LocalDateTime eventDate;

    private Location location;

    private Boolean paid;

    @Positive(groups = {ValidationMarker.OnUpdate.class, ValidationMarker.OnAdminUpdate.class},
            message = "Updating event category field must be positive!")
    private Long participantLimit;

    private Boolean requestModeration;

    @UpdateByUserStateActionValidation(groups = ValidationMarker.OnUpdate.class)
    @UpdateEventByAdminStateActionValidation(groups = ValidationMarker.OnAdminUpdate.class)
    private String stateAction;

    @UpdateEventTitleValidation(groups = {ValidationMarker.OnUpdate.class, ValidationMarker.OnAdminUpdate.class})
    private String title;

}
