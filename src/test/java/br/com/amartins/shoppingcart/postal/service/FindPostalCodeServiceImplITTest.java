package br.com.amartins.shoppingcart.postal.service;

import br.com.amartins.shoppingcart.postal.to.CountryEnum;
import br.com.amartins.shoppingcart.postal.to.PostalTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.wiremock.spring.EnableWireMock;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@EnableWireMock
public class FindPostalCodeServiceImplITTest {
    @Autowired
    private FindPostalCodeService findPostalCodeService;

    @Test
    void pesquisarCodigoPostalCorreiosValido() {
        Optional<PostalTO> postalTOOptional = findPostalCodeService.find(CountryEnum.BRAZIL, "123456000");
        assertTrue(postalTOOptional.isPresent());
        PostalTO postalTO = postalTOOptional.get();
        assertEquals("123456000", postalTO.postalCode());
        assertEquals( "Av Nove de Julho", postalTO.address());
        assertEquals( "São Paulo", postalTO.city());
        assertEquals( "SP", postalTO.state());
    }

    @Test
    void pesquisarCodigoPostalUSPSValido() {
        Optional<PostalTO> postalTOOptional = findPostalCodeService.find(CountryEnum.USA, "55555000");
        assertTrue(postalTOOptional.isPresent());
        PostalTO postalTO = postalTOOptional.get();
        assertEquals("55555000", postalTO.postalCode());
        assertEquals( "Ave 1st", postalTO.address());
        assertEquals( "New York", postalTO.city());
        assertEquals( "NY", postalTO.state());
    }
}
