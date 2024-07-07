package pl.dgorecki.shop_scrapper.utils;

import java.util.Optional;
import java.util.regex.MatchResult;
import java.util.regex.Pattern;

public final class RegexMatcher {

    private RegexMatcher() {}

    public static Optional<String> filter(String url, Pattern pattern) {
        return pattern
                .matcher(url)
                .results()
                .map(MatchResult::group)
                .findFirst();
    }
}
