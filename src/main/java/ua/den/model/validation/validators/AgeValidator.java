package ua.den.model.validation.validators;

import ua.den.model.validation.Age;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.LocalDate;
import java.time.Period;

public class AgeValidator implements ConstraintValidator<Age, LocalDate> {
    private int minAge;
    private int maxAge;

    @Override
    public void initialize(Age constraintAnnotation) {
        this.minAge = constraintAnnotation.minAge();
        this.maxAge = constraintAnnotation.maxAge();
    }

    @Override
    public boolean isValid(LocalDate passedLocalDate, ConstraintValidatorContext constraintValidatorContext) {
        LocalDate currentLocalDate = LocalDate.now();
        int ageDifference = Period.between(passedLocalDate, currentLocalDate).getYears();

        return (ageDifference >= minAge) && (ageDifference < maxAge);
    }
}
