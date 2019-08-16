package ua.den.model.dto;

import ua.den.model.validation.FieldsMatches;
import ua.den.model.validation.DoesCurrentPasswordMatches;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Objects;

@DoesCurrentPasswordMatches
@FieldsMatches(fieldName = "newPassword", repeatedFieldName = "repeatedNewPassword", invokedClass = UserPasswordDto.class,
        message = "{password.repeated.mismatched}")
public class UserPasswordDto implements Serializable {
    private static final long serialVersionUID = 892734897325470L;

    @NotBlank(message = "{field.blank}")
    private String currentPassword;

    @Size(min = 5, max = 28, message = "{password.new.pattern}")
    private String newPassword;

    @NotBlank(message = "{field.blank}")
    private String repeatedNewPassword;

    public String getCurrentPassword() {
        return currentPassword;
    }

    public void setCurrentPassword(String currentPassword) {
        this.currentPassword = currentPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public String getRepeatedNewPassword() {
        return repeatedNewPassword;
    }

    public void setRepeatedNewPassword(String repeatedNewPassword) {
        this.repeatedNewPassword = repeatedNewPassword;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserPasswordDto that = (UserPasswordDto) o;
        return Objects.equals(currentPassword, that.currentPassword) &&
                Objects.equals(newPassword, that.newPassword) &&
                Objects.equals(repeatedNewPassword, that.repeatedNewPassword);
    }

    @Override
    public int hashCode() {
        return Objects.hash(currentPassword, newPassword, repeatedNewPassword);
    }

    @Override
    public String toString() {
        return "UserPasswordDto{" +
                ", currentPassword='" + currentPassword + '\'' +
                ", newPassword='" + newPassword + '\'' +
                ", repeatedNewPassword='" + repeatedNewPassword + '\'' +
                '}';
    }
}
