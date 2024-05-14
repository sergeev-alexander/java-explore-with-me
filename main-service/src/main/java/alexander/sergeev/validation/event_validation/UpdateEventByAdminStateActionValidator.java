package alexander.sergeev.validation.event_validation;

import alexander.sergeev.model.EventAdminStateAction;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Arrays;

public class UpdateEventByAdminStateActionValidator
        implements ConstraintValidator<UpdateEventByAdminStateActionValidation, String> {

    @Override
    public boolean isValid(String updateAdminStateActionString, ConstraintValidatorContext context) {
        if (updateAdminStateActionString != null && Arrays.stream(EventAdminStateAction.values())
                .noneMatch((value) -> String.valueOf(value).equals(updateAdminStateActionString))) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("Updating admin stateAction field " +
                            "must be one of " + Arrays.toString(EventAdminStateAction.values()))
                    .addConstraintViolation();
            return false;
        }
        return true;
    }

}
