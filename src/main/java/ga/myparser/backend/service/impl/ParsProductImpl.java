package ga.myparser.backend.service.impl;

import ga.myparser.backend.service.ParsProduct;
import org.apache.commons.net.ftp.FTPClient;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;

@Service
public class ParsProductImpl implements ParsProduct {
    private static final double DOLLAR_RATE = 28.7;

    @Value("${custom.ftp.server}")
    private String server;

    @Value("${custom.ftp.port}")
    private int port;

    @Value("${custom.ftp.user}")
    private String user;

    @Value("${custom.ftp.pass}")
    private String pass;

    @Override
    public String getName(Document doc) {
        return doc.select("h1").text();
    }

    @Override
    public ArrayList<String> getListURLsImages(Document doc) {
        ArrayList listImages = new ArrayList();
        Elements elements = doc.select("div.album");
        for (Element element : elements.select("a.fancybox")) {
            listImages.add("http://free-run.kiev.ua/" + element.attr("href"));
        }
        return listImages;
    }

    @Override
    public LinkedHashSet<Integer> getOptions(Document doc) {
        LinkedHashSet listOptions = new LinkedHashSet();
        Elements elements = doc.select("div.actions");
        for (Element element : elements.select("option")) {
            listOptions.add(Integer.parseInt(element.text()));
        }
        return listOptions;
    }

    @Override
    public int getPrice(Document doc) {
        int price = Integer.parseInt(doc.select("div.actions")
                .select("div.price")
                .select("span")
                .text());
        return (int) (price * DOLLAR_RATE + 250);
    }

    @Override
    public Map<String,String> getMapTable(Document doc) {
        Elements elements = doc.select("table.wikitable");
        Map<String, String> mapTable = new HashMap<>();

        for (Element element : elements.select("tr")) {
            String leftColumn = element.select("td").get(0).text();
            String rightColumn = element.select("td").get(1).text();
            mapTable.put(leftColumn,rightColumn);
        }
        return mapTable;
    }

    @Override
    public void uploadImages(ArrayList<String> listURLsImages, String productName) throws IOException {
        int iterator = 1;
        FTPClient ftpClient = new FTPClient();
        ftpClient.setControlEncoding("CP1251");
        ftpClient.connect(server, port);
        ftpClient.login(user, pass);
        ftpClient.enterLocalPassiveMode();

        for (String URLImg : listURLsImages) {
            productName = productName.replace('/','-')
                    .replace(' ', '-')
                    .replace("\"", "")
                    .replace(".","");
            String fileName = productName + "-" + iterator++ + ".jpg";

            String remoteFile = "/sneakers.net.ua/www/image/new-image/" + fileName;
            try(InputStream inputStream = new URL(URLImg).openStream()){
                ftpClient.storeFile(remoteFile, inputStream);
            }
        }
    }
}
