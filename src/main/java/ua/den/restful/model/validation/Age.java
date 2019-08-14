package ua.den.restful.model.validation;


import ua.den.restful.model.validation.validators.AgeValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = AgeValidator.class)
@Target( { ElementType.METHOD, ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface Age {
    String message() default "{age.unacceptable}";
    int minAge() default 0;
    int maxAge() default 150;
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
