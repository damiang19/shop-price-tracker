package pl.dgorecki.scrapper.service.impl;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import pl.dgorecki.scrapper.enums.UrlRegexp;
import pl.dgorecki.scrapper.service.ScrapperService;
import pl.dgorecki.scrapper.service.ShopService;
import pl.dgorecki.scrapper.service.UrlValidatorService;
import pl.dgorecki.scrapper.service.dto.ScrappedProductDataDTO;
import pl.dgorecki.scrapper.service.dto.ShopDTO;
import pl.dgorecki.scrapper.utils.RegexMatcher;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
public class ScrapperServiceImpl implements ScrapperService {

    private final ShopService shopService;
    private final UrlValidatorService urlValidatorService;
    private final Logger log = LoggerFactory.getLogger(getClass());

    private final static Pattern pattern = Pattern.compile(UrlRegexp.PRICE.getValue());


    @Override
    public ScrappedProductDataDTO scrapActualProductPrice(String url) {
        String linkToProduct = urlValidatorService.validateUrlFormat(url);
        ShopDTO shopDTO = shopService.getByUrl(linkToProduct);
        String document = fetchWebsiteContent(linkToProduct);
        return downloadProductInfo(document, shopDTO);
    }

    @Override
    public String fetchWebsiteContent(String url) {
        StringBuilder output = new StringBuilder();
        try {
            String[] command = {"curl", "-X", "GET", url};

            ProcessBuilder processBuilder = new ProcessBuilder(command);
            Process process = processBuilder.start();

            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                output.append(line).append("\n");
            }

        } catch (IOException e) {
           throw new RuntimeException("error");
        }
        return output.toString();
    }

    @Override
    public ScrappedProductDataDTO downloadProductInfo(String loadedPage, ShopDTO shopDTO) {
        String productName = getProductName(loadedPage, shopDTO);
        String price = getProductPrice(loadedPage, shopDTO);
        price = price.replaceAll(" ", "");
        price = price.replaceAll(",", ".");
        BigDecimal productPrice = new BigDecimal(price);
        return new ScrappedProductDataDTO(productName, productPrice);
    }

    private String getProductPrice(String loadedPage, ShopDTO shopDTO) {
        return null;
//        return loadedPage
//                .getElementsByClass(shopDTO.getPriceHtmlClass())
//                .get(0).attributes().asList()
//                .stream().map(Attribute::getValue)
//                .filter(s -> s.matches(UrlRegexp.PRICE.getValue())).findFirst()
//                .orElse(RegexMatcher.filter(loadedPage.getElementsByClass(shopDTO.getPriceHtmlClass()).text(), pattern).orElse(""));
    }

    private String getProductName(String loadedPage, ShopDTO shopDTO) {
        return null;
//        String title = loadedPage.getElementsByClass(shopDTO.getProductNameHtmlClass()).text();
//        return title.isEmpty() ? loadedPage.getElementsByClass(shopDTO.getProductNameHtmlClass()).get(0).children().tagName("h2").get(0).text() : title;
    }
}
