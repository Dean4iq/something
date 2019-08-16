package ua.den.model.validation.validators;

import ua.den.model.validation.FieldsMatches;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.lang.reflect.Field;

public class PasswordMatchesValidator implements ConstraintValidator<FieldsMatches, Object> {
    private String passwordFieldName;
    private String repeatedPasswordFieldName;
    private Class invokedClass;

    @Override
    public void initialize(FieldsMatches constraintAnnotation) {
        this.passwordFieldName = constraintAnnotation.fieldName();
        this.repeatedPasswordFieldName = constraintAnnotation.repeatedFieldName();
        this.invokedClass = constraintAnnotation.invokedClass();
    }

    @Override
    public boolean isValid(Object object, ConstraintValidatorContext constraintValidatorContext) {
        try {
            Object password = getValueFromPrivateField(passwordFieldName, object);
            Object repeatedPassword = getValueFromPrivateField(repeatedPasswordFieldName, object);

            return password.equals(repeatedPassword);
        } catch (IllegalAccessException | NoSuchFieldException e) {
            e.printStackTrace();
        }

        return false;
    }

    private Object getValueFromPrivateField(String fieldName, Object invokedInstance)
            throws NoSuchFieldException, IllegalAccessException {
        Field field = invokedClass.getDeclaredField(fieldName);
        field.setAccessible(true);
        return field.get(invokedInstance);
    }
}
