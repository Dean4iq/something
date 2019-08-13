package ua.den.restful.model.dto;

import javax.validation.constraints.Pattern;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

public class UserDataDto implements Serializable {
    private static final long serialVersionUID = 8927345630325470L;

    @Pattern(regexp = "^[A-Z]{1}[a-z]{1,44}$", message = "Name is incorrect")
    private String name;

    @Pattern(regexp = "^[A-Z]{1}[a-z]{1,44}$", message = "Surname is incorrect")
    private String surname;

    private LocalDate birthDate;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserDataDto that = (UserDataDto) o;
        return Objects.equals(name, that.name) &&
                Objects.equals(surname, that.surname) &&
                Objects.equals(birthDate, that.birthDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, surname, birthDate);
    }

    @Override
    public String toString() {
        return "UserDataDto{" +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", birthDate=" + birthDate +
                '}';
    }
}
