package alexander.sergeev.validation.event_validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class UpdateEventTitleValidator implements ConstraintValidator<UpdateEventTitleValidation, String> {

    @Override
    public boolean isValid(String title, ConstraintValidatorContext context) {
        if (null != title && (title.length() < 3 || title.length() > 120)) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("Updating event title field is smaller than 3 " +
                            "or is bigger than 120 characters!")
                    .addConstraintViolation();
            return false;
        }
        return true;
    }

}
