package alexander.sergeev.validation.event_validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.LocalDateTime;

public class UpdateEventDateTimeValidator
        implements ConstraintValidator<UpdateEventDateTimeValidation, LocalDateTime> {

    @Override
    public boolean isValid(LocalDateTime eventDate, ConstraintValidatorContext context) {
        if (eventDate != null && eventDate.isBefore(LocalDateTime.now().plusHours(2L))) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("Updating event date time " +
                            "is earlier than 2 hours from the current moment!")
                    .addConstraintViolation();
            return false;
        }
        return true;
    }

}
