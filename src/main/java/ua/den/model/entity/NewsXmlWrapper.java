package ua.den.model.entity;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;

@XmlRootElement(name = "news", namespace = "https://den-edu.herokuapp.com/news")
@XmlAccessorType(XmlAccessType.FIELD)
public class NewsXmlWrapper {
    @XmlElement(name = "article")
    private List<NewsXML> newsXML = new ArrayList<>();

    public List<NewsXML> getNewsXML() {
        return newsXML;
    }

    public void setNewsXML(List<NewsXML> newsXML) {
        this.newsXML = newsXML;
    }
}
