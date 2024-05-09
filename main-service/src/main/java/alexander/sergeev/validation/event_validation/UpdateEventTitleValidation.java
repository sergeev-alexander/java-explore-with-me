package alexander.sergeev.validation.event_validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target(ElementType.FIELD)
@Retention(RUNTIME)
@Constraint(validatedBy = UpdateEventTitleValidator.class)
@Documented
public @interface UpdateEventTitleValidation {

    String message() default "Updating event title validation failed!";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
