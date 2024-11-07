package br.com.amartins.shoppingcart.postal.service;

import br.com.amartins.shoppingcart.postal.adapter.PostalNotFoundException;
import br.com.amartins.shoppingcart.postal.controller.ShippingCalculatorTO;
import br.com.amartins.shoppingcart.postal.to.CountryEnum;
import br.com.amartins.shoppingcart.postal.to.PostalTO;
import br.com.amartins.shoppingcart.postal.to.ShippingCostTO;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class BrazilShippingCalculatorStrategyImpl implements ShippingCalculatorStrategy {
    private final FindPostalCodeService findPostalCodeService;

    public BrazilShippingCalculatorStrategyImpl(final FindPostalCodeService findPostalCodeService) {
        this.findPostalCodeService = findPostalCodeService;
    }

    public boolean isSatisfiedBy(final ShippingCalculatorTO shippingCalculatorTO) {
        return shippingCalculatorTO.countryOrigin() == CountryEnum.BRAZIL;
    }

    public ShippingCostTO calculate(final ShippingCalculatorTO shippingCalculatorTO) {
        final PostalTO postalTOOrigin = findPostalCodeService.find(shippingCalculatorTO.countryOrigin(), shippingCalculatorTO.postalCodeOrigin())
                .orElseThrow(() -> new PostalNotFoundException(shippingCalculatorTO.countryOrigin(), shippingCalculatorTO.postalCodeOrigin()));
        final PostalTO postalTODestination = findPostalCodeService.find(shippingCalculatorTO.countryDestination(), shippingCalculatorTO.postalCodeDestination())
                .orElseThrow(() -> new PostalNotFoundException(shippingCalculatorTO.countryDestination(), shippingCalculatorTO.postalCodeDestination()));

        // Logica simples somente para exemplificar.
        if (postalTOOrigin.country().equals(postalTODestination.country())) {
            return new ShippingCostTO(postalTOOrigin, postalTODestination, BigDecimal.valueOf(10).multiply(shippingCalculatorTO.weight()));
        } else {
            return new ShippingCostTO(postalTOOrigin, postalTODestination, BigDecimal.valueOf(20).multiply(shippingCalculatorTO.weight()));
        }
    }
}
