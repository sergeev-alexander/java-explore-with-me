package alexander.sergeev.validation.event_validation;

import alexander.sergeev.model.EventUserStateAction;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Arrays;

public class UpdateEventStateActionValidator
        implements ConstraintValidator<UpdateEventStateActionValidation, String> {

    @Override
    public boolean isValid(String eventStateActionString, ConstraintValidatorContext context) {
        if (!(eventStateActionString == null) && Arrays.stream(EventUserStateAction.values())
                .noneMatch((value) -> String.valueOf(value).equals(eventStateActionString))) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("Updating event stateAction field " +
                            "must be one of " + Arrays.toString(EventUserStateAction.values()))
                    .addConstraintViolation();
            return false;
        }
        return true;
    }

}
