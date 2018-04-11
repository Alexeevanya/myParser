package ga.myparser.backend.util;

import java.util.Arrays;
import java.util.List;

public final class Utils {
    public static boolean urlIsValid(String url){
        return url.startsWith("http://free-run.kiev.ua/");
    }

    public static List<String> getListUrlsToParse(){
        List<String> catalogUrlsToParse = Arrays.asList(
                "http://free-run.kiev.ua/catalog/muzhskie-ugg-australia",
                "http://free-run.kiev.ua/catalog/zhenskie-ugg-australia",
                "http://free-run.kiev.ua/catalog/detskie-ugg-australia",
                "http://free-run.kiev.ua/catalog/adidas",
                "http://free-run.kiev.ua/catalog/asics",
                "http://free-run.kiev.ua/catalog/balenciaga",
                "http://free-run.kiev.ua/catalog/-diadora-",
                "http://free-run.kiev.ua/catalog/new-balance",
                "http://free-run.kiev.ua/catalog/nike",
                "http://free-run.kiev.ua/catalog/puma",
                "http://free-run.kiev.ua/catalog/reebok-kupit-kiev",
                "http://free-run.kiev.ua/catalog/sau",
                "http://free-run.kiev.ua/catalog/under-armour",
                "http://free-run.kiev.ua/catalog/basketbolnye-krossovki",
                "http://free-run.kiev.ua/catalog/adidas-zhenskiy",
                "http://free-run.kiev.ua/catalog/new-balanceW",
                "http://free-run.kiev.ua/catalog/balenciaga-w",
                "http://free-run.kiev.ua/catalog/fila",
                "http://free-run.kiev.ua/catalog/new-balanceG",
                "http://free-run.kiev.ua/catalog/nike-zhenskaya",
                "http://free-run.kiev.ua/catalog/saucony",
                "http://free-run.kiev.ua/catalog/puma2",
                "http://free-run.kiev.ua/catalog/reebok-cena",
                "http://free-run.kiev.ua/catalog/skechers",
                "http://free-run.kiev.ua/catalog/detskie-krossovki-adidas",
                "http://free-run.kiev.ua/catalog/detskie-krossovki-asics",
                "http://free-run.kiev.ua/catalog/detskie-krossovki-nike",
                "http://free-run.kiev.ua/catalog/nike-free-run-muzhskie",
                "http://free-run.kiev.ua/catalog/nike-free-run-zheskie",
                "http://free-run.kiev.ua/catalog/converse-cena",
                "http://free-run.kiev.ua/catalog/vans",
                "http://free-run.kiev.ua/catalog/toms",
                "http://free-run.kiev.ua/catalog/lacoste",
                "http://free-run.kiev.ua/catalog/timberland-"
        );
        return catalogUrlsToParse;
    }
}
