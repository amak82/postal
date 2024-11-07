package br.com.amartins.shoppingcart.postal.service;

import br.com.amartins.shoppingcart.postal.controller.ShippingCalculatorTO;
import br.com.amartins.shoppingcart.postal.to.ShippingCostTO;

public interface ShippingCalculatorStrategy {
    boolean isSatisfiedBy(ShippingCalculatorTO shippingCalculatorTO);
    ShippingCostTO calculate(ShippingCalculatorTO shippingCalculatorTO);
}
