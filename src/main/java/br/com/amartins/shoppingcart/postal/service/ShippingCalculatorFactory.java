package br.com.amartins.shoppingcart.postal.service;

import br.com.amartins.shoppingcart.postal.PostalException;
import br.com.amartins.shoppingcart.postal.controller.ShippingCalculatorTO;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ShippingCalculatorFactory {
    private List<ShippingCalculatorStrategy> shippingCalculatorStrategies;

    public ShippingCalculatorFactory(final List<ShippingCalculatorStrategy> shippingCalculatorStrategies) {
        this.shippingCalculatorStrategies = shippingCalculatorStrategies;
    }

    public ShippingCalculatorStrategy findStrategy(final ShippingCalculatorTO shippingCalculatorTO) {
        final Optional<ShippingCalculatorStrategy> shippingCalculatorStrategy = shippingCalculatorStrategies
                .stream()
                .filter(strategy -> strategy.isSatisfiedBy(shippingCalculatorTO))
                .findFirst();
        return shippingCalculatorStrategy.orElseThrow(() -> new PostalException("Strategy not found for the country " + shippingCalculatorTO.countryOrigin().name()));
    }
}
