package br.com.amartins.shoppingcart.postal.controller;

import br.com.amartins.shoppingcart.postal.service.FindPostalCodeService;
import br.com.amartins.shoppingcart.postal.to.CountryEnum;
import br.com.amartins.shoppingcart.postal.to.PostalTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(PostalCodeController.class)
@AutoConfigureMockMvc
class PostalCodeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private FindPostalCodeService findPostalCodeService;

    @Test
    void validarPesquisaDeCodigoPostal() throws Exception {
        when(findPostalCodeService.find(CountryEnum.BRAZIL, "123456789")).thenReturn(Optional.of(new PostalTO("123456789", "Av Rebouças", "São Paulo", "SP", "BRAZIL")));
        this.mockMvc
                .perform(get("/postalcode/v1/BRAZIL/123456789/address"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string("{\"postalCode\":\"123456789\",\"address\":\"Av Rebouças\",\"city\":\"São Paulo\",\"state\":\"SP\",\"country\":\"BRAZIL\"}"));
    }
}
