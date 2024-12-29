package pl.dgorecki.scrapper.utils;

import pl.dgorecki.scrapper.enums.UrlRegexp;
import pl.dgorecki.scrapper.service.errors.PatternNotFoundException;

import java.math.BigDecimal;
import java.util.regex.Pattern;

public final class BigDecimalConverter {

    private final static Pattern pricePattern = Pattern.compile(UrlRegexp.PRICE.getValue());

    private BigDecimalConverter() {}


    public static BigDecimal fromString(String price) {
        return new BigDecimal(RegexMatcher.filter(price, pricePattern)
                .orElseThrow(() -> new PatternNotFoundException("Cannot convert price to BigDecimal. Invalid String format.")));

    }
}
