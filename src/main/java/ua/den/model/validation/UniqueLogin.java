package ua.den.model.validation;

import ua.den.model.validation.validators.UniqueLoginValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = UniqueLoginValidator.class)
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface UniqueLogin {
    String message() default "{login.new.not_unique}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
