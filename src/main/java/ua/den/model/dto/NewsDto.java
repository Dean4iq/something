package ua.den.model.dto;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.OffsetDateTime;
import java.util.Objects;

@Getter
@Setter
public class NewsDto implements Serializable, Comparable<NewsDto> {
    private String header;
    private String description;
    private String text;
    private boolean displayable;
    private OffsetDateTime published;

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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof NewsDto)) return false;
        NewsDto newsDto = (NewsDto) o;
        return displayable == newsDto.displayable &&
                header.equals(newsDto.header) &&
                description.equals(newsDto.description) &&
                text.equals(newsDto.text) &&
                published.equals(newsDto.published);
    }

    @Override
    public int hashCode() {
        return Objects.hash(header, description, text, displayable, published);
    }

    @Override
    public int compareTo(NewsDto newsDto) {
        return this.getPublished().compareTo(newsDto.getPublished());
    }
}
