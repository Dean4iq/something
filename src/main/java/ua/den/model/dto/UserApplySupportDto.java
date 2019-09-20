package ua.den.model.dto;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.Objects;

public class UserApplySupportDto implements Serializable {
    private static final long serialVersionUID = 8927423443534534211L;

    @NotBlank(message = "field.blank")
    private String name;
    @NotBlank(message = "field.blank")
    private String email;
    @NotBlank(message = "field.blank")
    private String subject;
    @NotBlank(message = "field.blank")
    private String text;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserApplySupportDto that = (UserApplySupportDto) o;
        return name.equals(that.name) &&
                email.equals(that.email) &&
                subject.equals(that.subject) &&
                text.equals(that.text);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, email, subject, text);
    }
}
