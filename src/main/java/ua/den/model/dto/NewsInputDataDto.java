package ua.den.model.dto;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.sql.Date;

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
    private Date toBeDisplayedDate;

    public String getHeaderEn() {
        return headerEn;
    }

    public void setHeaderEn(String headerEn) {
        this.headerEn = headerEn;
    }

    public String getDescriptionEn() {
        return descriptionEn;
    }

    public void setDescriptionEn(String descriptionEn) {
        this.descriptionEn = descriptionEn;
    }

    public String getTextEn() {
        return textEn;
    }

    public void setTextEn(String textEn) {
        this.textEn = textEn;
    }

    public String getHeaderUa() {
        return headerUa;
    }

    public void setHeaderUa(String headerUa) {
        this.headerUa = headerUa;
    }

    public String getDescriptionUa() {
        return descriptionUa;
    }

    public void setDescriptionUa(String descriptionUa) {
        this.descriptionUa = descriptionUa;
    }

    public String getTextUa() {
        return textUa;
    }

    public void setTextUa(String textUa) {
        this.textUa = textUa;
    }

    public Date getToBeDisplayedDate() {
        return toBeDisplayedDate;
    }

    public void setToBeDisplayedDate(Date toBeDisplayedDate) {
        this.toBeDisplayedDate = toBeDisplayedDate;
    }
}
