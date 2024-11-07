package br.com.amartins.shoppingcart.postal.adapter.correios;

import br.com.amartins.shoppingcart.postal.to.CountryEnum;
import br.com.amartins.shoppingcart.postal.to.PostalTO;

public final class CorreiosPostalTOConvert {
    public static PostalTO convert(final CorreiosBrasilPostalCodeTO correiosTO) {
        if (correiosTO == null) {
            return null;
        }
        return new PostalTO(
                correiosTO.getCep(),
                correiosTO.getEndereco(),
                correiosTO.getCidade(),
                correiosTO.getEstado(),
                CountryEnum.BRAZIL.name()
                );
    }
}
