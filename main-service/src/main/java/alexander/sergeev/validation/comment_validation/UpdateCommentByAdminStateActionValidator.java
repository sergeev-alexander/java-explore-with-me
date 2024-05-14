package alexander.sergeev.validation.comment_validation;

import alexander.sergeev.model.CommentAdminStateAction;
import alexander.sergeev.validation.event_validation.UpdateEventByAdminStateActionValidation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Arrays;

public class UpdateCommentByAdminStateActionValidator
        implements ConstraintValidator<UpdateEventByAdminStateActionValidation, String> {

    @Override
    public boolean isValid(String updateAdminStateActionString, ConstraintValidatorContext context) {
        if (updateAdminStateActionString != null && Arrays.stream(CommentAdminStateAction.values())
                .noneMatch((value) -> String.valueOf(value).equals(updateAdminStateActionString))) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("Updating admin stateAction field " +
                            "must be one of " + Arrays.toString(CommentAdminStateAction.values()))
                    .addConstraintViolation();
            return false;
        }
        return true;
    }

}