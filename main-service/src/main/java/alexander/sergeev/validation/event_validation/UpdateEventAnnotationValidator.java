package alexander.sergeev.validation.event_validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class UpdateEventAnnotationValidator implements ConstraintValidator<UpdateEventAnnotationValidation, String> {

    @Override
    public boolean isValid(String title, ConstraintValidatorContext context) {
        if (null != title && (title.length() < 20 || title.length() > 2000)) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("Updating event annotation field is smaller than 20 " +
                            "or is bigger than 2000 characters!")
                    .addConstraintViolation();
            return false;
        }
        return true;
    }

}
