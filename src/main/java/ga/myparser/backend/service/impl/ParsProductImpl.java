package ga.myparser.backend.service.impl;

import ga.myparser.backend.service.ParsProduct;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.net.ftp.FTPClient;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.*;

@Slf4j
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
    public int getProductIdFromUrl(String productURL) {
        String[] parts = productURL.split("[-/]");
        return Integer.parseInt(parts[5]);
    }

    @Override
    public List<String> getListURLsImages(Document doc) {
        List<String> listImages = new ArrayList<>();
        Elements elements = doc.select("div.album");
        for (Element element : elements.select("a.fancybox")) {
            listImages.add("http://free-run.kiev.ua/" + element.attr("href"));
        }
        return listImages;
    }

    @Override
    public Set<Integer> getOptions(Document doc) {
        Set<Integer> listOptions = new HashSet<>();
        Elements elements = doc.select("div.actions");
        try {
            for (Element element : elements.select("option")) {
                int valueOption = (int)Double.parseDouble(element.text());
                listOptions.add(valueOption);
            }
        } catch (NumberFormatException e) {
            log.warn("NumberFormatException {}", doc.location());
        }
        if(listOptions.isEmpty()){
            log.warn("Empty list options {}", doc.location());
            return Collections.EMPTY_SET;
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
    public void uploadImages(List<String> listURLsImages, String productName) throws IOException {
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
