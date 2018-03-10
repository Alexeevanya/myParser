package ga.myparser.backend.util;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.*;

public class ProductUtil {
    private static final double DOLLAR_RATE = 28.7;

    public static String getName(Document doc) {
        return doc.select("h1").text();
    }

    public static ArrayList<String> getListURLsImages(Document doc) {
        ArrayList listImages = new ArrayList();
        Elements elements = doc.select("div.album");
        for (Element element : elements.select("a.fancybox")) {
            listImages.add("http://free-run.kiev.ua/" + element.attr("href"));
        }
        return listImages;
    }

    public static LinkedHashSet<Integer> getOptions(Document doc) {
        LinkedHashSet listOptions = new LinkedHashSet();
        Elements elements = doc.select("div.actions");
        for (Element element : elements.select("option")) {
            listOptions.add(Integer.parseInt(element.text()));
        }
        return listOptions;
    }

    public static int getPrice(Document doc) {
        int price = Integer.parseInt(doc.select("div.actions")
                .select("div.price")
                .select("span")
                .text());
        return (int) (price * DOLLAR_RATE + 250);
    }

    public static Map<String,String> getMapTable(Document doc) {
        Elements elements = doc.select("table.wikitable");
        Map<String, String> mapTable = new HashMap<>();

        for (Element element : elements.select("tr")) {
            String leftColumn = element.select("td").get(0).text();
            String rightColumn = element.select("td").get(1).text();
            mapTable.put(leftColumn,rightColumn);
        }
        return mapTable;
    }

    public static void uploadImages(ArrayList<String> listURLsImages, String productName) throws IOException {
        int iterator = 1;
        String server = "pinkshop.ftp.ukraine.com.ua";
        int port = 21;
        String user = "pinkshop_myparser";
        String pass = "ur83l93f";
        FTPClient ftpClient = new FTPClient();
        ftpClient.connect(server, port);
        ftpClient.login(user, pass);
        ftpClient.enterLocalPassiveMode();

        for (String URLImg : listURLsImages) {
            productName = productName.replace('/','-').replace(' ', '-');
            String fileName = productName + "-" + iterator++ + ".jpg";

            String remoteFile = "/inkshoes.com.ua/www/test-img/" + fileName;
            try(InputStream inputStream = new URL(URLImg).openStream()){
                ftpClient.storeFile(remoteFile, inputStream);
            }
        }
    }
}
