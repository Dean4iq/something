package ua.den.model.service;

import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;
import ua.den.model.dto.NewsDto;
import ua.den.model.dto.NewsInputDataDto;
import ua.den.model.entity.NewsXML;
import ua.den.model.entity.NewsXmlWrapper;

import javax.xml.XMLConstants;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class NewsXmlConverterService {
    public List<NewsDto> getNewsFromXmlAndConvertToDto(String language, Date date) {
        List<NewsXML> newsXMLs = receiveNewsXmlWrapper(language).getNewsXML();

        return mapToDtoAndLimitByDisplayableDate(newsXMLs, date);
    }

    public void addNewNewsDataToXml(NewsInputDataDto newsData, Date date) throws ParserConfigurationException, IOException, SAXException {
        try{
            JAXBContext jContext = JAXBContext.newInstance(NewsXmlWrapper.class);
            Marshaller marshallObj = jContext.createMarshaller();
            marshallObj.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

            NewsXmlWrapper newsXmlWrapperUa = new NewsXmlWrapper();
            NewsXML newsXMLUa = new NewsXML();
            newsXmlWrapperUa.setNewsXML(Collections.singletonList(newsXMLUa));
            newsXMLUa.setHeader(newsData.getHeaderUa());
            newsXMLUa.setDescription(newsData.getDescriptionUa());
            newsXMLUa.setText(newsData.getTextUa());
            newsXMLUa.setToBeDisplayed(newsData.getToBeDisplayedDate());
            newsXMLUa.setPublished(date);

            NewsXmlWrapper newsXmlWrapperEn = new NewsXmlWrapper();
            NewsXML newsXMLEn = new NewsXML();
            newsXmlWrapperEn.setNewsXML(Collections.singletonList(newsXMLEn));
            newsXMLEn.setHeader(newsData.getHeaderEn());
            newsXMLEn.setDescription(newsData.getDescriptionEn());
            newsXMLEn.setText(newsData.getTextEn());
            newsXMLEn.setToBeDisplayed(newsData.getToBeDisplayedDate());
            newsXMLEn.setPublished(date);

            marshallObj.marshal(newsXmlWrapperUa, new FileOutputStream("src/main/resources/news/news_uk.xml"));
            marshallObj.marshal(newsXmlWrapperEn, new FileOutputStream("src/main/resources/news/news_en.xml"));
        } catch(Exception e) {
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

    private List<NewsDto> mapToDtoAndLimitByDisplayableDate(List<NewsXML> newsXMLs, Date date) {
        return newsXMLs.stream()
                .map(elem -> elem.convertToDto(date))
                .filter(NewsDto::isDisplayable)
                .collect(Collectors.toList());
    }
}
