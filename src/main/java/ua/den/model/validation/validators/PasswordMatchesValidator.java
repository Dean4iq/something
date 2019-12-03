package ua.den.model.validation.validators;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ua.den.model.validation.FieldsMatches;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.lang.reflect.Field;

public class PasswordMatchesValidator implements ConstraintValidator<FieldsMatches, Object> {
    public static final Logger LOG = LoggerFactory.getLogger(PasswordMatchesValidator.class);

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
            LOG.error("Exception", e);
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
