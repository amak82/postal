package br.com.amartins.shoppingcart.postal.service;

import br.com.amartins.shoppingcart.postal.controller.ShippingCalculatorTO;
import br.com.amartins.shoppingcart.postal.to.ShippingCostTO;
import org.springframework.stereotype.Service;

@Service
public class ShippingCalculatorServiceImpl implements ShippingCalculatorService {
    private final ShippingCalculatorFactory shippingCalculatorFactory;

    public ShippingCalculatorServiceImpl(final ShippingCalculatorFactory shippingCalculatorFactory) {
        this.shippingCalculatorFactory = shippingCalculatorFactory;
    }

    public ShippingCostTO calculateShipping(final ShippingCalculatorTO shippingCalculatorTO) {
        return shippingCalculatorFactory.findStrategy(shippingCalculatorTO).calculate(shippingCalculatorTO);
    }
}
