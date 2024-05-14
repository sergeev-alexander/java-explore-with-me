package alexander.sergeev.validation.compilation_validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target(ElementType.FIELD)
@Retention(RUNTIME)
@Constraint(validatedBy = UpdateCompilationTitleValidator.class)
@Documented
public @interface UpdateCompilationTitleValidation {

    String message() default "Update compilation title validation failed!";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
