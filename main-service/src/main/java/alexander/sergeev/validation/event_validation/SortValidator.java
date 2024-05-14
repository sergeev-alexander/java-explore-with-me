package alexander.sergeev.validation.event_validation;

import alexander.sergeev.model.EventSort;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Arrays;

public class SortValidator
        implements ConstraintValidator<SortValidation, String> {

    @Override
    public boolean isValid(String sortString, ConstraintValidatorContext context) {
        if (sortString != null && Arrays.stream(EventSort.values())
                .noneMatch((value) -> String.valueOf(value).equals(sortString))) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("Sort request parameter " +
                            "must be one of " + Arrays.toString(EventSort.values()))
                    .addConstraintViolation();
            return false;
        }
        return true;
    }

}