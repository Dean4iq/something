package ua.den.restful.model.validation;

import ua.den.restful.model.validation.validators.CurrentPasswordValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = CurrentPasswordValidator.class)
@Target( { ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
public @interface DoesCurrentPasswordMatches {
    String message() default "Current password is invalid";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
