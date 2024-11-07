package br.com.amartins.shoppingcart.postal.controller;

import br.com.amartins.shoppingcart.postal.to.CountryEnum;
import br.com.amartins.shoppingcart.postal.to.PostalTO;
import br.com.amartins.shoppingcart.postal.service.FindPostalCodeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/postalcode/v1")
public class PostalCodeController {
    private final FindPostalCodeService findPostalCodeService;

    public PostalCodeController(final FindPostalCodeService findPostalCodeService) {
        this.findPostalCodeService = findPostalCodeService;
    }

    @GetMapping(value = "/{countryCode}/{postalCode}/address")
    public ResponseEntity<PostalTO> find(@PathVariable String countryCode, @PathVariable String postalCode) {
        final Optional<PostalTO> postalTOOptional = findPostalCodeService.find(CountryEnum.valueOf(countryCode), postalCode);
        return postalTOOptional
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}
