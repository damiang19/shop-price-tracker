package pl.dgorecki.scrapper.enums;

import lombok.Getter;

@Getter
public enum UrlRegexp {
    SHOP("(https?:\\/\\/(?:www\\.|(?!www))[a-zA-Z0-9][a-zA-Z0-9-]+[a-zA-Z0-9]\\.[a-zA-Z]{2,}" +
            "|(?:www\\.|(?!www))[a-zA-Z0-9][a-zA-Z0-9-]+[a-zA-Z0-9]\\.[a-zA-Z]{2,})") ,
    URL("(https?:\\/\\/(?:www\\.|(?!www))[a-zA-Z0-9][a-zA-Z0-9-]+[a-zA-Z0-9]\\." +
            "[^\\s]{2,}|www\\.[a-zA-Z0-9][a-zA-Z0-9-]+[a-zA-Z0-9]\\.[^\\s]{2,}|https?:\\/\\/(?:www\\.|" +
            "(?!www))[a-zA-Z0-9]+\\.[^\\s]{2,}|www\\.[a-zA-Z0-9]+\\.[^\\s]{2,})"),
    PRICE("\\d{1,3}( \\d{3})*(\\.\\d{2}|,\\d{2})?|\\d+(\\.\\d{2}|,\\d{2})?"),
    JSON("\\{[^{}]*+(?:\\{[^{}]*+\\}[^{}]*+)*\\}");

    private final String value;

    UrlRegexp(String value) {
        this.value = value;
    }

}
