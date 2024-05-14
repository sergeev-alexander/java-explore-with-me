package alexander.sergeev.validation.state_action_validation;

import alexander.sergeev.model.UserStateAction;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Arrays;

public class UpdateByUserStateActionValidator
        implements ConstraintValidator<UpdateByUserStateActionValidation, String> {

    @Override
    public boolean isValid(String userStateActionString, ConstraintValidatorContext context) {
        if (!(userStateActionString == null) && Arrays.stream(UserStateAction.values())
                .noneMatch((value) -> String.valueOf(value).equals(userStateActionString))) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("Updating user stateAction field " +
                            "must be one of " + Arrays.toString(UserStateAction.values()))
                    .addConstraintViolation();
            return false;
        }
        return true;
    }

}
