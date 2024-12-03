package alexander.sergeev.validation.comment_validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target(ElementType.FIELD)
@Retention(RUNTIME)
@Constraint(validatedBy = UpdateCommentByAdminStateActionValidator.class)
@Documented
public @interface UpdateCommentByAdminStateActionValidation {

    String message() default "Updating admin state action validation failed!";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
