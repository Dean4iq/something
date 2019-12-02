package ua.den.model.entity;

import ua.den.model.dto.TextInfoDto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Map;

@XmlRootElement(name = "words")
@XmlAccessorType(XmlAccessType.FIELD)
public class TextDescriptorXml {
    @XmlElement(name = "word")
    private String word;
    @XmlElement(name = "entry")
    private Map<String, Integer> relations;

    public TextInfoDto convertToDto() {
        TextInfoDto textInfoDto = new TextInfoDto();

        textInfoDto.setWord(word);
        textInfoDto.setUsages(relations);

        return textInfoDto;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public Map<String, Integer> getRelations() {
        return relations;
    }

    public void setRelations(Map<String, Integer> relations) {
        this.relations = relations;
    }
}
