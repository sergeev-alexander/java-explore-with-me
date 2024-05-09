package alexander.sergeev.validation.compilation_validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class UpdateCompilationTitleValidator implements ConstraintValidator<UpdateCompilationTitleValidation, String> {

    @Override
    public boolean isValid(String title, ConstraintValidatorContext context) {
        if (null != title && (title.length() < 1 || title.length() > 50)) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("Updating compilation title field is smaller than 1 " +
                            "or is bigger than 50 characters!")
                    .addConstraintViolation();
            return false;
        }
        return true;
    }

}
