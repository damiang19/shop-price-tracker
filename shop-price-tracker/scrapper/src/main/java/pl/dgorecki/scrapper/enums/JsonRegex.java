package pl.dgorecki.scrapper.enums;

import lombok.Getter;

@Getter
public enum JsonRegex {

    JSON("\\{[^{}]*+(?:\\{[^{}]*+\\}[^{}]*+)*\\}"),
    INNER_JSON("\\{[^{}]*+(?:\\{[^{}]*+\\}[^{}]*+).*\\}"),
    MEDIA_JSON("<script\\\\s+[^>]*type\\\\s*=\\\\s*['\\\"]application/ld\\\\+json['\\\"][^>]*>(.*?)</script>");

    private final String value;

    JsonRegex(String value) {
        this.value = value;
    }
}
