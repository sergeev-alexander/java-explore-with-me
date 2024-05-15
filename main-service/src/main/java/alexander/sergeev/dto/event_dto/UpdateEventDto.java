package alexander.sergeev.dto.event_dto;

import alexander.sergeev.model.Location;
import alexander.sergeev.validation.ValidationMarker;
import alexander.sergeev.validation.event_validation.*;
import alexander.sergeev.validation.event_validation.UpdateEventByAdminStateActionValidation;
import alexander.sergeev.validation.user_state_action_validation.UpdateByUserStateActionValidation;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import javax.validation.constraints.Positive;
import java.time.LocalDateTime;

import static alexander.sergeev.formatter.FormatterDateTime.DATE_TIME_PATTERN;

@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UpdateEventDto {

    @UpdateEventAnnotationValidation(groups = {ValidationMarker.OnUpdate.class, ValidationMarker.OnAdminUpdate.class})
    String annotation;

    @Positive(groups = {ValidationMarker.OnUpdate.class, ValidationMarker.OnAdminUpdate.class},
            message = "Updating event category field must be positive!")
    Long category;

    @UpdateEventDescriptionValidation(groups = {ValidationMarker.OnUpdate.class, ValidationMarker.OnAdminUpdate.class})
    String description;

    @UpdateEventDateTimeValidation(groups = ValidationMarker.OnUpdate.class)
    @UpdateEventByAdminDateTimeValidation(groups = ValidationMarker.OnAdminUpdate.class)
    @JsonFormat(pattern = DATE_TIME_PATTERN)
    LocalDateTime eventDate;

    Location location;

    Boolean paid;

    @Positive(groups = {ValidationMarker.OnUpdate.class, ValidationMarker.OnAdminUpdate.class},
            message = "Updating event category field must be positive!")
    Long participantLimit;

    Boolean requestModeration;

    @UpdateByUserStateActionValidation(groups = ValidationMarker.OnUpdate.class)
    @UpdateEventByAdminStateActionValidation(groups = ValidationMarker.OnAdminUpdate.class)
    String stateAction;

    @UpdateEventTitleValidation(groups = {ValidationMarker.OnUpdate.class, ValidationMarker.OnAdminUpdate.class})
    String title;

}
