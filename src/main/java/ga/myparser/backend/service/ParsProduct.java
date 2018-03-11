package ga.myparser.backend.service;

import org.jsoup.nodes.Document;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.Map;

public interface ParsProduct {

    String getName(Document doc);

    ArrayList<String> getListURLsImages(Document doc);

    void uploadImages(ArrayList<String> listURLsImages, String productName) throws IOException;

    LinkedHashSet<Integer> getOptions(Document doc);

    int getPrice(Document doc);

    Map<String,String> getMapTable(Document doc);

    int getProductIdFromUrl(String productURL);
}
