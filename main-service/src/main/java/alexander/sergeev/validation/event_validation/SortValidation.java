package alexander.sergeev.validation.event_validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target(ElementType.PARAMETER)
@Retention(RUNTIME)
@Constraint(validatedBy = SortValidator.class)
@Documented
public @interface SortValidation {

    String message() default "Sort validation failed!";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
