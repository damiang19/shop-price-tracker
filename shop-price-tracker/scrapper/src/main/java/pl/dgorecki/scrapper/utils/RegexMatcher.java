package pl.dgorecki.scrapper.utils;

import java.util.List;
import java.util.Optional;
import java.util.regex.MatchResult;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public final class RegexMatcher {

    private RegexMatcher() {}

    public static Optional<String> filter(String url, Pattern pattern) {
        return pattern
                .matcher(url)
                .results()
                .map(MatchResult::group)
                .findFirst();
    }

    public static List<String> findAll(String url, Pattern pattern) {
        return pattern
                .matcher(url)
                .results()
                .map(MatchResult::group)
                .collect(Collectors.toList());
    }
}
