package pl.dgorecki.scrapper.service.impl;

import org.springframework.stereotype.Service;
import pl.dgorecki.scrapper.enums.JsonRegex;
import pl.dgorecki.scrapper.enums.UrlRegex;
import pl.dgorecki.scrapper.service.UrlValidatorService;
import pl.dgorecki.scrapper.service.errors.PatternNotFoundException;
import pl.dgorecki.scrapper.utils.RegexMatcher;


import java.util.List;
import java.util.regex.Pattern;

@Service
public class UrlValidatorServiceImpl implements UrlValidatorService {

    private static final Pattern productUrlRegexp = Pattern.compile(UrlRegex.URL.getValue());
    private static final Pattern shopUrlRegexp = Pattern.compile(UrlRegex.SHOP.getValue());
    private static final Pattern websiteInnerJsonRegexp = Pattern.compile(JsonRegex.INNER_JSON.getValue());
    private static final Pattern websiteBasicJsonRegexp = Pattern.compile(JsonRegex.BASIC_JSON.getValue());
    private static final Pattern websiteInnerMediaJsonRegexp = Pattern.compile(
            JsonRegex.MEDIA_JSON.getValue(),
            Pattern.DOTALL | Pattern.CASE_INSENSITIVE);


    @Override
    public String validateUrlFormat(String url) {
        return RegexMatcher.filter(url, productUrlRegexp).orElseThrow(() -> new PatternNotFoundException("Invalid URL format"));
    }

    @Override
    public String getBaseShopUrl(String url) {
        return RegexMatcher.filter(url, shopUrlRegexp).orElseThrow(() -> new PatternNotFoundException("URL is not correctly formatted"));
    }

    @Override
    public List<String> extractJsons(String page, JsonRegex jsonRegex) {
        return switch (jsonRegex) {
            case INNER_JSON -> RegexMatcher.findAll(page, websiteInnerJsonRegexp);
            case MEDIA_JSON -> RegexMatcher.findAllAndGroup(page, websiteInnerMediaJsonRegexp);
            case BASIC_JSON -> RegexMatcher.findAll(page, websiteBasicJsonRegexp);
        };
    }
}
