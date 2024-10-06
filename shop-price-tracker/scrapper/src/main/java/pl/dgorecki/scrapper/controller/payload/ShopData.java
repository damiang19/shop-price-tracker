package pl.dgorecki.scrapper.controller.payload;

import jakarta.validation.constraints.NotBlank;

public record ShopData(@NotBlank String productNameHtmlClass,
                       @NotBlank String priceHtmlClass,
                       @NotBlank String name,
                       @NotBlank String shopUrl) {}
