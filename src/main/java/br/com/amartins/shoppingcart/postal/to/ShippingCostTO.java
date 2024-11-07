package br.com.amartins.shoppingcart.postal.to;

import java.math.BigDecimal;

public record ShippingCostTO(PostalTO postalTOOrigin, PostalTO postalTODestination, BigDecimal cost) {
}
