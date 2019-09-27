package ua.den.model.service;

import org.springframework.stereotype.Service;
import ua.den.model.dto.NewsDto;
import ua.den.model.entity.NewsXML;
import ua.den.model.entity.NewsXmlWrapper;

import javax.xml.XMLConstants;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import java.io.File;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class NewsXmlConverterService {
    public List<NewsDto> getNewsFromXmlAndConvertToDto(String language, Date date) {
        List<NewsXML> newsXMLs = receiveNewsXmlWrapper(language).getNewsXML();

        return mapToDtoAndLimitByDisplayableDate(newsXMLs, date);
    }

    public List<NewsDto> sortNewsByPublishDate(List<NewsDto> newsList) {
        return newsList.stream().sorted().collect(Collectors.toList());
    }

    private NewsXmlWrapper receiveNewsXmlWrapper(String language) {
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(NewsXmlWrapper.class);
            Unmarshaller jaxbUnmarshal = jaxbContext.createUnmarshaller();
            SchemaFactory sf = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
            File schemaURL = new File("src/main/resources/news/news.xsd");
            Schema schema = sf.newSchema(schemaURL);
            jaxbUnmarshal.setSchema(schema);
            return (NewsXmlWrapper) jaxbUnmarshal.unmarshal(new File("src/main/resources/news/news_" + language + ".xml"));
        } catch (Exception e) {
            System.out.println(e.toString());
        }

        return new NewsXmlWrapper();
    }

    private List<NewsDto> mapToDtoAndLimitByDisplayableDate(List<NewsXML> newsXMLs, Date date) {
        return newsXMLs.stream()
                .map(elem -> elem.convertToDto(date))
                .filter(NewsDto::isDisplayable)
                .collect(Collectors.toList());
    }
}
