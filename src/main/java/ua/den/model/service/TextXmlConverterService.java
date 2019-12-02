package ua.den.model.service;

import ua.den.model.dto.TextInfoDto;
import ua.den.model.entity.TextDataXml;
import ua.den.model.entity.TextDescriptorXml;

import javax.xml.XMLConstants;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import java.io.File;
import java.io.FileOutputStream;
import java.util.List;
import java.util.stream.Collectors;

public class TextXmlConverterService {
    public List<TextInfoDto> getTextsFromXmlAndConvertToDto() {
        List<TextDescriptorXml> newsXMLs = receiveTextXmlWrapper().getTextDescriptors();

        return mapToDtoAndLimitByDisplayableDate(newsXMLs);
    }

    public void addNewTextDataToXml(TextInfoDto textData) {
        try {
            JAXBContext jContext = JAXBContext.newInstance(TextDataXml.class);
            Marshaller marshallObj = jContext.createMarshaller();
            marshallObj.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);

            TextDataXml textElements = receiveTextXmlWrapper();

            TextDescriptorXml textDescriptor = new TextDescriptorXml();
            textElements.getTextDescriptors().add(textDescriptor);

            textDescriptor.setWord(textData.getWord());
            textDescriptor.setRelations(textData.getUsages());

            marshallObj.marshal(textElements, new FileOutputStream("src/main/resources/texts/texts.xml"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private TextDataXml receiveTextXmlWrapper() {
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(TextDataXml.class);
            Unmarshaller jaxbUnmarshal = jaxbContext.createUnmarshaller();
            SchemaFactory sf = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
            File schemaURL = new File("src/main/resources/texts/texts.xsd");
            Schema schema = sf.newSchema(schemaURL);
            jaxbUnmarshal.setSchema(schema);
            return (TextDataXml) jaxbUnmarshal.unmarshal(new File("src/main/resources/texts/texts.xml"));
        } catch (Exception e) {
            System.out.println(e.toString());
        }

        return new TextDataXml();
    }

    private List<TextInfoDto> mapToDtoAndLimitByDisplayableDate(List<TextDescriptorXml> newsXMLs) {
        return newsXMLs.stream()
                .map(TextDescriptorXml::convertToDto)
                .collect(Collectors.toList());
    }
}
