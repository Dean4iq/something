package ua.den.model.entity;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;

@XmlRootElement(name = "text", namespace = "https://den-edu.herokuapp.com/texts")
@XmlAccessorType(XmlAccessType.FIELD)
public class TextDataXml {
    @XmlElement(name = "words")
    private List<TextDescriptorXml> textDescriptors = new ArrayList<>();

    public List<TextDescriptorXml> getTextDescriptors() {
        return textDescriptors;
    }

    public void setTextDescriptors(List<TextDescriptorXml> textDescriptors) {
        this.textDescriptors = textDescriptors;
    }
}
