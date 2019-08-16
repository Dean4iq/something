package ua.den.model.dto;

import org.springframework.format.annotation.DateTimeFormat;
import ua.den.model.validation.Age;
import ua.den.model.validation.FieldsMatches;
import ua.den.model.validation.UniqueLogin;

import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

@FieldsMatches(fieldName = "password", repeatedFieldName = "repeatedPassword",
        invokedClass = UserRegistrationDTO.class, message = "{password.repeated.mismatched}")
public class UserRegistrationDTO implements Serializable {
    private static final long serialVersionUID = 89273489732940L;

    @Pattern(regexp = "^[A-z0-9._]{6,18}$", message = "{login.new.pattern}")
    @UniqueLogin
    private String login;

    @Size(min = 5, max = 28, message = "{password.new.pattern}")
    private String password;

    @NotBlank(message = "{field.blank}")
    private String repeatedPassword;

    @Pattern(regexp = "^[A-Z]{1}[a-z]{1,44}$", message = "{pattern.name}")
    private String name;

    @Pattern(regexp = "^[A-Z]{1}[a-z]{1,44}$", message = "{pattern.surname}")
    private String surname;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Age(minAge = 14, maxAge = 75)
    private LocalDate birthDate;

    @AssertTrue(message = "{agreement.not_accepted}")
    private boolean rulesAccepted;

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRepeatedPassword() {
        return repeatedPassword;
    }

    public void setRepeatedPassword(String repeatedPassword) {
        this.repeatedPassword = repeatedPassword;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public boolean isRulesAccepted() {
        return rulesAccepted;
    }

    public void setRulesAccepted(boolean rulesAccepted) {
        this.rulesAccepted = rulesAccepted;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserRegistrationDTO that = (UserRegistrationDTO) o;
        return rulesAccepted == that.rulesAccepted &&
                Objects.equals(login, that.login) &&
                Objects.equals(password, that.password) &&
                Objects.equals(repeatedPassword, that.repeatedPassword) &&
                Objects.equals(name, that.name) &&
                Objects.equals(surname, that.surname) &&
                Objects.equals(birthDate, that.birthDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(login, password, repeatedPassword, name, surname, birthDate, rulesAccepted);
    }

    @Override
    public String toString() {
        return "UserRegistrationDTO{" +
                "login='" + login + '\'' +
                ", password='" + password + '\'' +
                ", repeatedPassword='" + repeatedPassword + '\'' +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", birthDate=" + birthDate +
                ", rulesAccepted=" + rulesAccepted +
                '}';
    }
}
