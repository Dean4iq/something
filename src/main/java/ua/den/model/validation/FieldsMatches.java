package ua.den.model.validation;

import ua.den.model.validation.validators.PasswordMatchesValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = PasswordMatchesValidator.class)
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface FieldsMatches {
    String message();

    String fieldName();

    String repeatedFieldName();

    Class invokedClass();

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
