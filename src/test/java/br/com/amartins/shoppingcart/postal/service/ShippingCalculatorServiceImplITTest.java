package br.com.amartins.shoppingcart.postal.service;

import br.com.amartins.shoppingcart.postal.adapter.PostalNotFoundException;
import br.com.amartins.shoppingcart.postal.controller.ShippingCalculatorTO;
import br.com.amartins.shoppingcart.postal.to.CountryEnum;
import br.com.amartins.shoppingcart.postal.to.ShippingCostTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.wiremock.spring.EnableWireMock;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@EnableWireMock
public class ShippingCalculatorServiceImplITTest {
    @Autowired
    private ShippingCalculatorServiceImpl shippingCalculatorService;

    @Value("${wiremock.server.baseUrl}")
    private String wiremockUrl;
    private ShippingCalculatorTO shippingCalculatorTO;
    private ShippingCostTO shippingCostTO;

    @Test
    public void validarFreteNacional() {
        dadoFreteEntreEnderecosDentroDoMesmoPais();
        quandoCalcularFrete();
        deveTerCalculadoFreteNacional();
    }

    @Test
    public void validarFreteInternacional() {
        dadoFreteEntreEnderecosEmPaisesDiferentes();
        quandoCalcularFrete();
        assertEquals(BigDecimal.valueOf(2000), shippingCostTO.cost());
    }

    @Test
    public void validarFreteParaCodigoPostalInvalido() {
        dadoFreteEntreEnderecosComCodigoPostalInvalido();
        assertEquals("Postal code 123456001 not found for the country BRAZIL",
                assertThrows(PostalNotFoundException.class, this::quandoCalcularFrete).getMessage());
    }

    private void deveTerCalculadoFreteNacional() {
        assertEquals(BigDecimal.valueOf(1000), shippingCostTO.cost());
    }

    private void quandoCalcularFrete() {
        shippingCostTO = shippingCalculatorService.calculateShipping(shippingCalculatorTO);
    }

    private void dadoFreteEntreEnderecosDentroDoMesmoPais() {
        shippingCalculatorTO = new ShippingCalculatorTO(CountryEnum.BRAZIL, "123456000", CountryEnum.BRAZIL, "98765000", BigDecimal.valueOf(100));
    }

    private void dadoFreteEntreEnderecosEmPaisesDiferentes() {
        shippingCalculatorTO = new ShippingCalculatorTO(CountryEnum.BRAZIL, "123456000", CountryEnum.USA, "55555000", BigDecimal.valueOf(100));
    }

    private void dadoFreteEntreEnderecosComCodigoPostalInvalido() {
        shippingCalculatorTO = new ShippingCalculatorTO(CountryEnum.BRAZIL, "123456001", CountryEnum.BRAZIL, "98765000", BigDecimal.valueOf(100));
    }
}
