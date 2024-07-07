package pl.dgorecki.shop_scrapper.service.impl;

import org.springframework.stereotype.Service;
import pl.dgorecki.shop_scrapper.enums.UrlRegexp;
import pl.dgorecki.shop_scrapper.service.UrlValidatorService;
import pl.dgorecki.shop_scrapper.service.errors.InvalidUrlException;
import pl.dgorecki.shop_scrapper.utils.RegexMatcher;

import java.util.regex.Pattern;

@Service
public class UrlValidatorServiceImpl implements UrlValidatorService {

    private static final Pattern productUrlRegexp = Pattern.compile(UrlRegexp.URL.getValue());
    private static final Pattern shopUrlRegexp = Pattern.compile(UrlRegexp.SHOP.getValue());


    @Override
    public String validateUrlFormat(String url) {
        return RegexMatcher.filter(url, productUrlRegexp).orElseThrow(() -> new InvalidUrlException("Invalid URL format"));
    }

    @Override
    public String getBaseShopUrl(String url) {
        return RegexMatcher.filter(url, shopUrlRegexp).orElseThrow(() -> new InvalidUrlException("URL is not correctly formatted."));
    }
}
