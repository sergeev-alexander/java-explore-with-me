package alexander.sergeev.validation.request_validation;

import alexander.sergeev.model.RequestUpdateStatus;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Arrays;

public class UpdateRequestStatusValidator
        implements ConstraintValidator<UpdateRequestStatusValidation, String> {

    @Override
    public boolean isValid(String requestStatusString, ConstraintValidatorContext context) {
        if (Arrays.stream(RequestUpdateStatus.values())
                .noneMatch((value) -> String.valueOf(value).equals(requestStatusString))) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("Updating request status field " +
                            "must be one of " + Arrays.toString(RequestUpdateStatus.values()))
                    .addConstraintViolation();
            return false;
        }
        return true;
    }

}
