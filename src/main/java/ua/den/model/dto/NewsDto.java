package ua.den.model.dto;

import java.io.Serializable;
import java.time.OffsetDateTime;

public class NewsDto implements Serializable, Comparable<NewsDto> {
    private String header;
    private String description;
    private String text;
    private boolean displayable;
    private OffsetDateTime published;

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

    public boolean isDisplayable() {
        return displayable;
    }

    public void setDisplayable(boolean displayable) {
        this.displayable = displayable;
    }

    public OffsetDateTime getPublished() {
        return published;
    }

    public void setPublished(OffsetDateTime published) {
        this.published = published;
    }

    @Override
    public String toString() {
        return "NewsDto{" +
                "header='" + header + '\'' +
                ", description='" + description + '\'' +
                ", text='" + text + '\'' +
                ", displayable=" + displayable +
                ", published=" + published +
                '}';
    }

    @Override
    public int compareTo(NewsDto newsDto) {
        return this.getPublished().compareTo(newsDto.getPublished());
    }
}
