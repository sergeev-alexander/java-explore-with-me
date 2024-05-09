package alexander.sergeev.validation.event_validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.LocalDateTime;

public class NewEventDateTimeValidator implements ConstraintValidator<NewEventDateTimeValidation, LocalDateTime> {

    @Override
    public boolean isValid(LocalDateTime eventDate, ConstraintValidatorContext context) {
        if (eventDate.isBefore(LocalDateTime.now().plusHours(2L))) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("Creating event date time " +
                            "is earlier than 2 hours from the current moment!")
                    .addConstraintViolation();
            return false;
        }
        return true;
    }

}
