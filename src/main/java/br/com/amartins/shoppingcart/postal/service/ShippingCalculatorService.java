package br.com.amartins.shoppingcart.postal.service;

import br.com.amartins.shoppingcart.postal.controller.ShippingCalculatorTO;
import br.com.amartins.shoppingcart.postal.to.ShippingCostTO;

public interface ShippingCalculatorService {
    ShippingCostTO calculateShipping(final ShippingCalculatorTO shippingCalculatorTO);
}
