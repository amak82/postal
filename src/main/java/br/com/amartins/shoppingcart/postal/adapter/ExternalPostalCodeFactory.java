package br.com.amartins.shoppingcart.postal.adapter;

import br.com.amartins.shoppingcart.postal.PostalException;
import br.com.amartins.shoppingcart.postal.to.CountryEnum;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ExternalPostalCodeFactory {
    private List<PostalCodeAdapter> postalCodeAdapters;

    public ExternalPostalCodeFactory(final List<PostalCodeAdapter> postalCodeAdapters) {
        this.postalCodeAdapters = postalCodeAdapters;
    }

    public PostalCodeAdapter findAdapter(final CountryEnum country) {
        final Optional<PostalCodeAdapter> postalCodeAdapter = postalCodeAdapters
                .stream()
                .filter(adapter -> adapter.isSatisfiedBy(country))
                .findFirst();
        return postalCodeAdapter.orElseThrow(() -> new PostalException("Factory not found to the country " + country.name()));
    }
}
