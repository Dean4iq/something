package ua.den.model.service;

import org.springframework.stereotype.Service;
import ua.den.model.dto.NewsDto;
import ua.den.model.dto.NewsInputDataDto;
import ua.den.model.entity.NewsXML;
import ua.den.model.entity.NewsXmlWrapper;

import javax.xml.XMLConstants;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import java.io.File;
import java.io.FileOutputStream;
import java.sql.Date;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class NewsXmlConverterService {
    public List<NewsDto> getNewsFromXmlAndConvertToDto(String language, OffsetDateTime date) {
        List<NewsXML> newsXMLs = receiveNewsXmlWrapper(language).getNewsXML();

        return mapToDtoAndLimitByDisplayableDate(newsXMLs, date);
    }

    public void addNewNewsDataToXml(NewsInputDataDto newsData, OffsetDateTime currentDateTime) {
        System.out.println(newsData);
        try {
            JAXBContext jContext = JAXBContext.newInstance(NewsXmlWrapper.class);
            Marshaller marshallObj = jContext.createMarshaller();
            marshallObj.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);

            NewsXmlWrapper newsElementsUA = receiveNewsXmlWrapper("uk");
            NewsXmlWrapper newsElementsEN = receiveNewsXmlWrapper("en");

            NewsXML newsXMLUa = new NewsXML();
            newsElementsUA.getNewsXML().add(newsXMLUa);

            newsXMLUa.setHeader(newsData.getHeaderUa());
            newsXMLUa.setDescription(newsData.getDescriptionUa());
            newsXMLUa.setText(newsData.getTextUa());
            newsXMLUa.setToBeDisplayed(Date.from(newsData.getToBeDisplayedDate().toInstant()));
            newsXMLUa.setPublished(Date.from(currentDateTime.toInstant()));

            NewsXML newsXMLEn = new NewsXML();
            newsElementsEN.getNewsXML().add(newsXMLEn);

            newsXMLEn.setHeader(newsData.getHeaderEn());
            newsXMLEn.setDescription(newsData.getDescriptionEn());
            newsXMLEn.setText(newsData.getTextEn());
            newsXMLEn.setToBeDisplayed(Date.from(newsData.getToBeDisplayedDate().toInstant()));
            newsXMLEn.setPublished(Date.from(currentDateTime.toInstant()));

            marshallObj.marshal(newsElementsUA, new FileOutputStream("src/main/resources/news/news_uk.xml"));
            marshallObj.marshal(newsElementsEN, new FileOutputStream("src/main/resources/news/news_en.xml"));
        } catch (Exception e) {
            e.printStackTrace();
        }
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

    private List<NewsDto> mapToDtoAndLimitByDisplayableDate(List<NewsXML> newsXMLs, OffsetDateTime date) {
        return newsXMLs.stream()
                .map(elem -> elem.convertToDto(date))
                .filter(NewsDto::isDisplayable)
                .collect(Collectors.toList());
    }
}
