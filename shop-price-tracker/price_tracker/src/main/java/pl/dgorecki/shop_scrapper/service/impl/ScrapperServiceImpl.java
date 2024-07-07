package pl.dgorecki.shop_scrapper.service.impl;

import lombok.RequiredArgsConstructor;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Attribute;
import org.jsoup.nodes.Document;
import org.jsoup.parser.Parser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import pl.dgorecki.shop_scrapper.enums.UrlRegexp;
import pl.dgorecki.shop_scrapper.service.ScrapperService;
import pl.dgorecki.shop_scrapper.service.dto.ScrappedProductData;
import pl.dgorecki.shop_scrapper.service.dto.ShopDTO;
import pl.dgorecki.shop_scrapper.utils.RegexMatcher;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Optional;
import java.util.regex.MatchResult;
import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
public class ScrapperServiceImpl implements ScrapperService {

    private final Logger log = LoggerFactory.getLogger(getClass());
    private final static Pattern pattern = Pattern.compile(UrlRegexp.PRICE.getValue());


    @Override
    public ScrappedProductData scrapActualProductPrice(ShopDTO shopDTO, String url) {
        Document document = connectToTrackedProductSite(url);
        return downloadProductInfo(document, shopDTO);
    }

    @Override
    public Document connectToTrackedProductSite(String url) {
        try {
            return Jsoup.connect(url).userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/58.0.3029.110 Safari/537.3")
                    .referrer("http://www.google.com")
                    .userAgent("Mozilla/5.0")
                    .header("Accept-Language", "en-US,en;q=0.5")
                    .timeout(10 * 1000) // timeout w milisekundach
                    .followRedirects(true)
                    .ignoreContentType(true)
                    .ignoreHttpErrors(true)
                    .parser(Parser.xmlParser()).get();
        } catch (IOException connectionException) {
            throw new RuntimeException("Error - cannot connect with url : " + connectionException);
        }
    }

    @Override
    public ScrappedProductData downloadProductInfo(Document loadedPage, ShopDTO shopDTO) {
        String productName = getProductName(loadedPage, shopDTO);
        String price = getProductPrice(loadedPage, shopDTO);
        price = price.replaceAll(" ", "");
        price = price.replaceAll(",", ".");
        BigDecimal productPrice = new BigDecimal(price);
        return new ScrappedProductData(productName, productPrice);
    }

    private String getProductPrice(Document loadedPage, ShopDTO shopDTO) {
        return loadedPage
                .getElementsByClass(shopDTO.getPriceHtmlClass())
                .get(0).attributes().asList()
                .stream().map(Attribute::getValue)
                .filter(s -> s.matches(UrlRegexp.PRICE.getValue())).findFirst()
                .orElse(RegexMatcher.filter(loadedPage.getElementsByClass(shopDTO.getPriceHtmlClass()).text(), pattern).orElse(""));
    }

    private String getProductName(Document loadedPage, ShopDTO shopDTO) {
        String title = loadedPage.getElementsByClass(shopDTO.getProductNameHtmlClass()).text();
        return title.isEmpty() ? loadedPage.getElementsByClass(shopDTO.getProductNameHtmlClass()).get(0).children().tagName("h2").get(0).text() : title;
    }
}
