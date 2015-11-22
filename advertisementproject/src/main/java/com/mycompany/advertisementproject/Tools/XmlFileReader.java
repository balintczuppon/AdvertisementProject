package com.mycompany.advertisementproject.Tools;

import com.mycompany.advertisementproject.UIs.Views.LogInView;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.DOMException;
import org.xml.sax.SAXException;

public class XmlFileReader {

    public static final String fileSource = "/Users/balin/data.xml";

    private LogInView loginView;
    
    private String tagName;

    public void readXml() {
        try {
            File fXmlFile = new File(fileSource);
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(fXmlFile);
            doc.getDocumentElement().normalize();

            NodeList nList = doc.getElementsByTagName(tagName);

            for (int temp = 0; temp < nList.getLength(); temp++) {
                Node nNode = nList.item(temp);
                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element eElement = (Element) nNode;
                    selectTagName(eElement);
                }
            }
        } catch (ParserConfigurationException | SAXException | IOException | DOMException ex) {
            Logger.getLogger(XmlFileReader.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void selectTagName(Element eElement) {
        switch (tagName) {
            case "LogInView": {
                loginView.getLblTitle().setValue(eElement.getElementsByTagName("title").item(0).getTextContent());
                loginView.getTfEmail().setCaption(eElement.getElementsByTagName("email").item(0).getTextContent());
                loginView.getPfPassWord().setCaption(eElement.getElementsByTagName("password").item(0).getTextContent());
                loginView.getBtnLogin().setCaption(eElement.getElementsByTagName("loginbutton").item(0).getTextContent());
                break;
            }
            case "RegistrationView": {
                break;
            }
            case "AdvertListView": {
                break;
            }
        }
    }

    public void setTagName(String tagName) {
        this.tagName = tagName;
    }

    public void setLoginView(LogInView loginView) {
        this.loginView = loginView;
    }
}
