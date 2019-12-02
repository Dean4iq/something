package ua.den.model.dto;

import java.util.Map;

public class TextInfoDto {
    private String word;
    private Map<String, Integer> usages;

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public Map<String, Integer> getUsages() {
        return usages;
    }

    public void setUsages(Map<String, Integer> usages) {
        this.usages = usages;
    }

    @Override
    public String toString() {
        return "TextInfoDto{" +
                "word='" + word + '\'' +
                ", usages=" + usages +
                '}';
    }
}
