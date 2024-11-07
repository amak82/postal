package br.com.amartins.shoppingcart.postal.adapter.usps;

import br.com.amartins.shoppingcart.postal.adapter.PostalCodeAdapter;
import br.com.amartins.shoppingcart.postal.to.CountryEnum;
import br.com.amartins.shoppingcart.postal.to.PostalTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.Optional;

@Service
public class USPSPostalCodeAdapterImpl implements PostalCodeAdapter {
    private RestClient restClient;

    public boolean isSatisfiedBy(final CountryEnum countryEnum) {
        return countryEnum == CountryEnum.USA;
    }

    public USPSPostalCodeAdapterImpl(@Value( "${usps.url}") final String baseUrl) {
        this.restClient = RestClient.builder()
                .baseUrl(baseUrl)
                .build();
    }
    public Optional<PostalTO> find(final String postalCode) {
        return restClient.get()
                .uri("/" + postalCode + "/address")
                .accept(MediaType.APPLICATION_JSON)
                .exchange((request, response) -> {
                    if (response.getStatusCode().is4xxClientError()) {
                        return Optional.empty();
                    }
                    final USPSPostalCodeTO uspsPostalCodeTO = response.bodyTo(USPSPostalCodeTO.class);
                    if (uspsPostalCodeTO == null) {
                        return Optional.empty();
                    }
                    return Optional.of(USPSPostalTOConvert.convert(uspsPostalCodeTO));
                });
    }
}
