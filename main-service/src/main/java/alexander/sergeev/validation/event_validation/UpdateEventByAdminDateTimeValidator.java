package alexander.sergeev.validation.event_validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.LocalDateTime;

public class UpdateEventByAdminDateTimeValidator
        implements ConstraintValidator<UpdateEventByAdminDateTimeValidation, LocalDateTime> {

    @Override
    public boolean isValid(LocalDateTime eventDate, ConstraintValidatorContext context) {
        if (eventDate != null && eventDate.isBefore(LocalDateTime.now().plusHours(1L))) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("Updating event date time " +
                            "is earlier than 1 hour from the current moment!")
                    .addConstraintViolation();
            return false;
        }
        return true;
    }

}
