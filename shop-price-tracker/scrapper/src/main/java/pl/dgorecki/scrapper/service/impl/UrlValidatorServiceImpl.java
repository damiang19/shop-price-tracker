package pl.dgorecki.scrapper.service.impl;

import org.springframework.stereotype.Service;
import pl.dgorecki.scrapper.enums.UrlRegexp;
import pl.dgorecki.scrapper.service.UrlValidatorService;
import pl.dgorecki.scrapper.service.errors.PatternNotFoundException;
import pl.dgorecki.scrapper.utils.RegexMatcher;


import java.util.List;
import java.util.regex.Pattern;

@Service
public class UrlValidatorServiceImpl implements UrlValidatorService {

    private static final Pattern productUrlRegexp = Pattern.compile(UrlRegexp.URL.getValue());
    private static final Pattern shopUrlRegexp = Pattern.compile(UrlRegexp.SHOP.getValue());
    private static final Pattern websiteJsonRegexp = Pattern.compile(UrlRegexp.JSON.getValue());


    @Override
    public String validateUrlFormat(String url) {
        return RegexMatcher.filter(url, productUrlRegexp).orElseThrow(() -> new PatternNotFoundException("Invalid URL format"));
    }

    @Override
    public String getBaseShopUrl(String url) {
        return RegexMatcher.filter(url, shopUrlRegexp).orElseThrow(() -> new PatternNotFoundException("URL is not correctly formatted"));
    }

    @Override
    public List<String> extractJson(String page) {
        return RegexMatcher.findAll(page, websiteJsonRegexp);
    }
}
