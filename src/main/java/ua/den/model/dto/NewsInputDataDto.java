package ua.den.model.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;

@Getter
@Setter
public class NewsInputDataDto implements Serializable {
    @NotEmpty(message = "field.blank")
    private String headerEn;
    @NotEmpty(message = "field.blank")
    private String descriptionEn;
    @NotEmpty(message = "field.blank")
    private String textEn;
    @NotEmpty(message = "field.blank")
    private String headerUa;
    @NotEmpty(message = "field.blank")
    private String descriptionUa;
    @NotEmpty(message = "field.blank")
    private String textUa;
    private String toBeDisplayedDate;

    public OffsetDateTime convertToBeDisplayedDateTime() {
        return OffsetDateTime.parse(toBeDisplayedDate + " +0000", DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss Z"));
    }

    @Override
    public String toString() {
        return "NewsInputDataDto{" +
                "headerEn='" + headerEn + '\'' +
                ", descriptionEn='" + descriptionEn + '\'' +
                ", textEn='" + textEn + '\'' +
                ", headerUa='" + headerUa + '\'' +
                ", descriptionUa='" + descriptionUa + '\'' +
                ", textUa='" + textUa + '\'' +
                ", toBeDisplayedDate=" + toBeDisplayedDate +
                '}';
    }
}
