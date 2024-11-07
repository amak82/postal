package br.com.amartins.shoppingcart.postal.service;

import br.com.amartins.shoppingcart.postal.to.CountryEnum;
import br.com.amartins.shoppingcart.postal.to.PostalTO;

import java.util.Optional;

public interface FindPostalCodeService {
    Optional<PostalTO> find(final CountryEnum country, final String postalCode);
}
