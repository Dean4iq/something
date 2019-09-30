package ua.den.model.entity;

import ua.den.model.dto.NewsDto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.Date;

@XmlRootElement(name = "article")
@XmlAccessorType(XmlAccessType.FIELD)
public class NewsXML implements Serializable {
    @XmlElement(name = "header")
    private String header;
    @XmlElement(name = "description")
    private String description;
    @XmlElement(name = "text")
    private String text;
    @XmlElement(name = "to_be_displayed")
    private Date toBeDisplayed;
    @XmlElement(name = "published")
    private Date published;

    public NewsDto convertToDto(OffsetDateTime date) {
        NewsDto dto = new NewsDto();

        dto.setHeader(this.header);
        dto.setDescription(this.description);
        dto.setText(this.text);
        dto.setDisplayable(isNewsDisplayable(date));
        dto.setPublished(published.toInstant().atOffset(ZoneOffset.UTC));

        return dto;
    }

    private boolean isNewsDisplayable(OffsetDateTime date) {
        return (toBeDisplayed == null) || (date.compareTo(toBeDisplayed.toInstant().atOffset(ZoneOffset.UTC)) > 0);
    }

    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Date getToBeDisplayed() {
        return toBeDisplayed;
    }

    public void setToBeDisplayed(Date toBeDisplayed) {
        this.toBeDisplayed = toBeDisplayed;
    }

    public Date getPublished() {
        return published;
    }

    public void setPublished(Date published) {
        this.published = published;
    }
}
