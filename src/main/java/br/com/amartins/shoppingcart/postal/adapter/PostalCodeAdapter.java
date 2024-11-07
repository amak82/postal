package br.com.amartins.shoppingcart.postal.adapter;

import br.com.amartins.shoppingcart.postal.to.CountryEnum;
import br.com.amartins.shoppingcart.postal.to.PostalTO;

import java.util.Optional;

public interface PostalCodeAdapter {
    boolean isSatisfiedBy(final CountryEnum countryEnum);
    Optional<PostalTO> find(final String postalCode);
}
