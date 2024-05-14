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
@Constraint(validatedBy = UpdateEventDateTimeValidator.class)
@Documented
public @interface UpdateEventDateTimeValidation {

    String message() default "Update by user event date time validation failed!";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
