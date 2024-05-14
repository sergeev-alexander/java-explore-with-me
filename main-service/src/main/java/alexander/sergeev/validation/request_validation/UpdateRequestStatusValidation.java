package alexander.sergeev.validation.request_validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target(ElementType.FIELD)
@Retention(RUNTIME)
@Constraint(validatedBy = UpdateRequestStatusValidator.class)
@Documented
public @interface UpdateRequestStatusValidation {

    String message() default "Updating request status validation failed!";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
