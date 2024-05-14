package alexander.sergeev.validation.comment_validation;

import alexander.sergeev.validation.event_validation.UpdateEventTitleValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target(ElementType.FIELD)
@Retention(RUNTIME)
@Constraint(validatedBy = UpdateCommentTextValidator.class)
@Documented
public @interface UpdateCommentTextValidation {

    String message() default "Updating comment text validation failed!";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
