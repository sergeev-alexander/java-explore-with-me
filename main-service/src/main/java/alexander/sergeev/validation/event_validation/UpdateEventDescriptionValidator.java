package alexander.sergeev.validation.event_validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class UpdateEventDescriptionValidator implements ConstraintValidator<UpdateEventDescriptionValidation, String> {

    @Override
    public boolean isValid(String title, ConstraintValidatorContext context) {
        if (null != title && (title.length() < 20 || title.length() > 7000)) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("Updating event description field is smaller than 20 " +
                            "or is bigger than 7000 characters!")
                    .addConstraintViolation();
            return false;
        }
        return true;
    }

}