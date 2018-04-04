package ga.myparser.backend.service;

import org.jsoup.nodes.Document;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Set;

public interface ParsProduct {

    String getName(Document doc);

    List<String> getListURLsImages(Document doc);

    void uploadImages(List<String> listURLsImages, String productName) throws IOException;

    Set getOptions(Document doc);

    int getPrice(Document doc);

    Map<String,String> getMapTable(Document doc);

    int getProductIdFromUrl(String productURL);
}
