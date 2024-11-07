package br.com.amartins.shoppingcart.postal.service;

import br.com.amartins.shoppingcart.postal.adapter.ExternalPostalCodeFactory;
import br.com.amartins.shoppingcart.postal.adapter.PostalCodeAdapter;
import br.com.amartins.shoppingcart.postal.dao.PostalCodeDAO;
import br.com.amartins.shoppingcart.postal.entity.PostalEntity;
import br.com.amartins.shoppingcart.postal.to.CountryEnum;
import br.com.amartins.shoppingcart.postal.to.PostalTO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class FindPostalCodeServiceImplTest {
    @InjectMocks
    private FindPostalCodeServiceImpl findPostalCodeService;

    @Mock
    private ExternalPostalCodeFactory externalPostalCodeFactory;

    @Mock
    private PostalCodeDAO postalCodeDAO;

    @Mock
    private PostalCodeAdapter postalCodeAdapter;

    private Optional<PostalTO> postalTOOptional;

    @Test
    void pesquisarCodigoPostalExternamente() {
        dadoCodigoPostalNaoEncontradoNoCache();
        dadoCodigoPostalRetornadoPeloServicoExterno();
        quandoPesquisarOCodigoPostal();
        deveRetornarUmCodigoPostalValido();
        deveTerSalvoEmCache();
    }

    @Test
    void pesquisarCodigoPostalNoCache() {
        dadoCodigoPostalEncontradoNoCache();
        quandoPesquisarOCodigoPostal();
        deveRetornarUmCodigoPostalValido();
        naoDeveTerSalvoEmCache();
    }

    private void dadoCodigoPostalRetornadoPeloServicoExterno() {
        final PostalTO externalPostalTO = new PostalTO("123456000", "Av Nove de Julho, 900", "São Paulo", "SP", CountryEnum.BRAZIL.name());
        when(externalPostalCodeFactory.findAdapter(CountryEnum.BRAZIL)).thenReturn(postalCodeAdapter);
        when(postalCodeAdapter.find("123456000")).thenReturn(Optional.of(externalPostalTO));
    }

    private void dadoCodigoPostalNaoEncontradoNoCache() {
        when(postalCodeDAO.findLastPostalCode("123456000", CountryEnum.BRAZIL.name())).thenReturn(null);
    }

    private void dadoCodigoPostalEncontradoNoCache() {
        final PostalEntity postalEntity = new PostalEntity();
        postalEntity.setPostalCode("123456000");
        postalEntity.setAddress("Av Nove de Julho, 900");
        postalEntity.setCity("São Paulo");
        postalEntity.setState("SP");
        postalEntity.setCountry( CountryEnum.BRAZIL.name());
        when(postalCodeDAO.findLastPostalCode("123456000", CountryEnum.BRAZIL.name())).thenReturn(postalEntity);
    }

    private void deveRetornarUmCodigoPostalValido() {
        assertTrue(postalTOOptional.isPresent());
        PostalTO postalTO = postalTOOptional.get();
        assertEquals("123456000", postalTO.postalCode());
        assertEquals( "Av Nove de Julho, 900", postalTO.address());
        assertEquals( "São Paulo", postalTO.city());
        assertEquals( "SP", postalTO.state());
    }

    private void quandoPesquisarOCodigoPostal() {
        postalTOOptional = findPostalCodeService.find(CountryEnum.BRAZIL, "123456000");
    }

    private void deveTerSalvoEmCache() {
        verify(postalCodeDAO, times(1)).save(argThat(postalEntity -> "123456000".equals(postalEntity.getPostalCode())));
    }

    private void naoDeveTerSalvoEmCache() {
        verify(postalCodeDAO, never()).save(any());
    }
}
