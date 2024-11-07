package br.com.amartins.shoppingcart.postal.adapter.correios;

import br.com.amartins.shoppingcart.postal.adapter.PostalCodeAdapter;
import br.com.amartins.shoppingcart.postal.to.CountryEnum;
import br.com.amartins.shoppingcart.postal.to.PostalTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.Optional;

@Service
public class CorreiosBrasilPostalCodeAdapterImpl implements PostalCodeAdapter {
    private RestClient restClient;

    public boolean isSatisfiedBy(final CountryEnum countryEnum) {
        return countryEnum == CountryEnum.BRAZIL;
    }

    public CorreiosBrasilPostalCodeAdapterImpl(@Value("${correios.url}") final String baseUrl) {
        this.restClient = RestClient.builder()
                .baseUrl(baseUrl)
                .build();
    }
    public Optional<PostalTO> find(final String postalCode) {
        return restClient.get()
                .uri("/" + postalCode + "/endereco")
                .accept(MediaType.APPLICATION_JSON)
                .exchange((request, response) -> {
                    if (response.getStatusCode().is4xxClientError()) {
                        return Optional.empty();
                    }
                    final CorreiosBrasilPostalCodeTO correiosTO = response.bodyTo(CorreiosBrasilPostalCodeTO.class);
                    if (correiosTO == null) {
                        return Optional.empty();
                    }
                    return Optional.of(CorreiosPostalTOConvert.convert(correiosTO));
                });
    }
}
