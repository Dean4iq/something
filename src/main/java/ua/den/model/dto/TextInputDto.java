package ua.den.model.dto;

import javax.validation.constraints.NotBlank;

public class TextInputDto {
    @NotBlank(message = "field.blank")
    private String textData;

    public String getTextData() {
        return textData;
    }

    public void setTextData(String textData) {
        this.textData = textData;
    }
}
