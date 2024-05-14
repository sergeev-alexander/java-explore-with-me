package alexander.sergeev.validation.comment_validation;

import alexander.sergeev.validation.event_validation.UpdateEventTitleValidation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class UpdateCommentTextValidator
        implements ConstraintValidator<UpdateEventTitleValidation, String> {

    @Override
    public boolean isValid(String title, ConstraintValidatorContext context) {
        if (null != title && (title.length() < 2 || title.length() > 2000)) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("Updating comment text field is smaller than 2 " +
                            "or is bigger than 2000 characters!")
                    .addConstraintViolation();
            return false;
        }
        return true;
    }

}
