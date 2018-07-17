package ga.myparser.backend.service.common.impl;

import ga.myparser.backend.service.common.CommonService;
import ga.myparser.backend.service.common.ProductFreeRunToUpdate;
import ga.myparser.backend.service.common.ProductPoolPartyToUpdate;
import ga.myparser.backend.service.poolparty.PoolPartyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.net.URL;
import java.util.*;

@Service
public class CommonServiceImpl implements CommonService {

    @Autowired
    private PoolPartyService poolPartyService;

    @Override
    public void updateSneakersPoolParty() {
        poolPartyService.start();
    }

    @Override
    public List<ProductPoolPartyToUpdate> getProductsToUpdatePoolParty() {
        List<ProductPoolPartyToUpdate> productPoolPartyListToUpdate = new ArrayList<>();

        try (InputStream xml = new URL("http://poolparty.ua/files/POOLPARTY-XML-50.xml").openStream()) {
            DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            Document doc = builder.parse(xml);
            doc.getDocumentElement().normalize();

            NodeList offersNodeList = doc.getElementsByTagName("offer");

            for (int i = 0; i < offersNodeList.getLength(); i++) {
                if (offersNodeList.item(i).getNodeType() == Node.ELEMENT_NODE) {
                    ProductPoolPartyToUpdate product = new ProductPoolPartyToUpdate();
                    Node node = doc.getElementsByTagName("offer").item(i);
                    NodeList nodeList = node.getChildNodes();
                    int n = nodeList.getLength();
                    Node current;

                    for (int j = 0; j < n; j++) {
                        current = nodeList.item(j);
                        if (current.getNodeType() == Node.ELEMENT_NODE) {
                            switch (current.getNodeName()) {
                                case "model": {
                                    product.setModel(current.getTextContent());
                                }
                                break;
                                case "price": {
                                    BigDecimal price = new BigDecimal(Double.valueOf(current.getTextContent()));
                                    product.setPrice(price);
                                }
                                break;
                            }
                        }
                    }
                    product.setQuantity(1000);
                    productPoolPartyListToUpdate.add(product);
                }
            }
        } catch (ParserConfigurationException | IOException | SAXException e) {
            e.printStackTrace();
        }
        return productPoolPartyListToUpdate;
    }

    @Override
    public List<ProductFreeRunToUpdate> getProductsToUpdateFreeRun() {
        List<ProductFreeRunToUpdate> productFreeRunToUpdates = new ArrayList<>();
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

        try (InputStream xml = new URL("https://sneakers.net.ua/freeRun.xml").openStream()) {
            DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            Document doc = builder.parse(xml);
            doc.getDocumentElement().normalize();

            NodeList offersNodeList = doc.getElementsByTagName("offer");

            for (int i = 0; i < offersNodeList.getLength(); i++) {
                if (offersNodeList.item(i).getNodeType() == Node.ELEMENT_NODE) {
                    ProductFreeRunToUpdate product = new ProductFreeRunToUpdate();

                    Node node = doc.getElementsByTagName("offer").item(i);
                    Element element = (Element) node;
                    NodeList nodeList = node.getChildNodes();
                    int n = nodeList.getLength();
                    Node current;

                    for (int j = 0; j < n; j++) {
                        current = nodeList.item(j);
                        if (current.getNodeType() == Node.ELEMENT_NODE) {

                            switch (current.getNodeName()) {
                                case "url": {
                                    product.setModel(element.getAttribute("id"));
                                }
                                break;
                                case "price": {
                                    BigDecimal price = getPrice(current.getTextContent());
                                    product.setPrice(price);
                                }
                            }
                        }
                    }
                    product.setQuantity(1000);
                    productFreeRunToUpdates.add(product);
                }
            }
        } catch (ParserConfigurationException | IOException | SAXException e) {
            e.printStackTrace();
        }
        return productFreeRunToUpdates;
    }

    private BigDecimal getPrice(String textContent) {
        double value = Double.valueOf(textContent);
        value = value * 26.5 + 250;
        int intPrice = (int) value;
        return new BigDecimal(intPrice);
    }
}
