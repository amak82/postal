package br.com.amartins.shoppingcart.postal.controller;

import br.com.amartins.shoppingcart.postal.to.CountryEnum;

import java.math.BigDecimal;

public class ShippingCalculatorForm {
    private String countryOrigin;
    private String postalCodeOrigin;
    private String countryDestination;
    private String postalCodeDestination;
    private BigDecimal weight;

    public String getCountryOrigin() {
        return countryOrigin;
    }

    public void setCountryOrigin(String countryOrigin) {
        this.countryOrigin = countryOrigin;
    }

    public String getPostalCodeOrigin() {
        return postalCodeOrigin;
    }

    public void setPostalCodeOrigin(String postalCodeOrigin) {
        this.postalCodeOrigin = postalCodeOrigin;
    }

    public String getCountryDestination() {
        return countryDestination;
    }

    public void setCountryDestination(String countryDestination) {
        this.countryDestination = countryDestination;
    }

    public String getPostalCodeDestination() {
        return postalCodeDestination;
    }

    public void setPostalCodeDestination(String postalCodeDestination) {
        this.postalCodeDestination = postalCodeDestination;
    }

    public BigDecimal getWeight() {
        return weight;
    }

    public void setWeight(BigDecimal weight) {
        this.weight = weight;
    }

    public ShippingCalculatorTO toShippingCalculatorTO() {
        return new ShippingCalculatorTO(CountryEnum.valueOf(countryOrigin), postalCodeOrigin, CountryEnum.valueOf(countryDestination), postalCodeDestination, weight);
    }
}
