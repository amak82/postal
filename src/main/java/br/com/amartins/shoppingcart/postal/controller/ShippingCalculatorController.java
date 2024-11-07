package br.com.amartins.shoppingcart.postal.controller;

import br.com.amartins.shoppingcart.postal.adapter.PostalNotFoundException;
import br.com.amartins.shoppingcart.postal.service.ShippingCalculatorService;
import br.com.amartins.shoppingcart.postal.to.ShippingCostTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/shipping/calculator/v1")
public class ShippingCalculatorController {
    private static final Logger LOGGER = LoggerFactory.getLogger(ShippingCalculatorController.class);
    private final ShippingCalculatorService shippingCalculatorService;
    public ShippingCalculatorController(final ShippingCalculatorService shippingCalculatorService) {
        this.shippingCalculatorService = shippingCalculatorService;
    }

    @PostMapping
    public ResponseEntity<ShippingCostTO> calculateShipping(@RequestBody ShippingCalculatorForm shippingCalculatorForm) {
        try {
            final ShippingCostTO shippingTO = shippingCalculatorService.calculateShipping(shippingCalculatorForm.toShippingCalculatorTO());
            return ResponseEntity.ok(shippingTO);
        } catch (final PostalNotFoundException e) {
            LOGGER.warn(e.getMessage(), e);
            return ResponseEntity.notFound().build();
        } catch (final Exception e) {
            LOGGER.error("Fail to calculate shipping", e);
            return ResponseEntity.badRequest().build();
        }
    }
}
