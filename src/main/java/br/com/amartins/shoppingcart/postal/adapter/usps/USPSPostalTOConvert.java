package br.com.amartins.shoppingcart.postal.adapter.usps;

import br.com.amartins.shoppingcart.postal.to.CountryEnum;
import br.com.amartins.shoppingcart.postal.to.PostalTO;

public final class USPSPostalTOConvert {
    public static PostalTO convert(final USPSPostalCodeTO uspsPostalCodeTO) {
        if (uspsPostalCodeTO == null) {
            return null;
        }
        return new PostalTO(
                uspsPostalCodeTO.getPostalCode(),
                uspsPostalCodeTO.getAddress(),
                uspsPostalCodeTO.getCity(),
                uspsPostalCodeTO.getState(),
                CountryEnum.USA.name()
                );
    }
}
