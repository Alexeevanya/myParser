package ga.myparser.backend.service.poolparty.impl;

import ga.myparser.backend.dao.ProductDAO;
import ga.myparser.backend.service.poolparty.PoolPartyService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
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

@Slf4j
@Service
@RequiredArgsConstructor
public class PoolPartyServiceImpl implements PoolPartyService {

    private final ProductDAO productDAO;

    @Scheduled(initialDelay = 5000, fixedDelay = 4320 * 10000)
    private void scheduleStart() {
        log.info("Update PoolParty Service started");
        start();
        log.info("Update PoolParty Service finished");
    }


    public void start(){
        int idManufacturerPoolParty = productDAO.getIdManufacturerByName("PoolParty");

        List<String> allProductsPoolParty = productDAO.getAllModelProductsPoolParty(idManufacturerPoolParty);

        List<ProductPoolPartyToUpdate> listToUpdate = getProductsToUpdateFromXML();

        updateProducts(listToUpdate);

        List<String> listToZeroQuantity = getListToZeroQuantity(allProductsPoolParty, listToUpdate);

        updateQuantity(listToZeroQuantity);

    }

    @Override
    public List<ProductPoolPartyToUpdate> getProductsToUpdateFromXML() {
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

    private void updateProducts(List<ProductPoolPartyToUpdate> listToUpdate) {
        for (ProductPoolPartyToUpdate product : listToUpdate) {
            productDAO.updateProductsPoolParty(product.model, product.quantity, product.price);
        }
    }

    private List<String> getListToZeroQuantity(List<String> allProductsPoolParty, List<ProductPoolPartyToUpdate> listToUpdate) {
        List<String> updatedProducts = new ArrayList<>();
        for (ProductPoolPartyToUpdate product : listToUpdate) {
            updatedProducts.add(product.model);
        }
        allProductsPoolParty.removeAll(updatedProducts);
        return allProductsPoolParty;
    }

    private void updateQuantity(List<String> listToZeroQuantity) {
        for (String productModel : listToZeroQuantity) {
            productDAO.updateQuantity(productModel);
        }
    }

    @Getter
    @Setter
    public class ProductPoolPartyToUpdate {
        private String model;
        private int quantity;
        private BigDecimal price;
    }
}


