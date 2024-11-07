package br.com.amartins.shoppingcart.postal.adapter;

import br.com.amartins.shoppingcart.postal.to.CountryEnum;

import static java.lang.String.format;

public class PostalNotFoundException extends RuntimeException {
    public PostalNotFoundException(final CountryEnum country,
                                   final String postalCode) {
        super(format("Postal code %s not found for the country %s", postalCode, country));
    }
}
