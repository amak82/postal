package br.com.amartins.shoppingcart.postal.controller;

import br.com.amartins.shoppingcart.postal.to.CountryEnum;

import java.math.BigDecimal;

public record ShippingCalculatorTO(CountryEnum countryOrigin, String postalCodeOrigin, CountryEnum countryDestination, String postalCodeDestination, BigDecimal weight) {
}
