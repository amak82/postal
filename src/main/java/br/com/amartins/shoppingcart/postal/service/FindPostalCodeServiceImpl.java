package br.com.amartins.shoppingcart.postal.service;

import br.com.amartins.shoppingcart.postal.adapter.ExternalPostalCodeFactory;
import br.com.amartins.shoppingcart.postal.dao.PostalCodeDAO;
import br.com.amartins.shoppingcart.postal.to.CountryEnum;
import br.com.amartins.shoppingcart.postal.entity.PostalEntity;
import br.com.amartins.shoppingcart.postal.to.PostalTO;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class FindPostalCodeServiceImpl implements FindPostalCodeService {
    private final ExternalPostalCodeFactory externalPostalCodeFactory;
    private final PostalCodeDAO postalCodeDAO;

    public FindPostalCodeServiceImpl(final ExternalPostalCodeFactory externalPostalCodeFactory, final PostalCodeDAO postalCodeDAO) {
        this.externalPostalCodeFactory = externalPostalCodeFactory;
        this.postalCodeDAO = postalCodeDAO;
    }

    public Optional<PostalTO> find(final CountryEnum country, final String postalCode) {
        Optional<PostalTO> fromCache = findFromCache(country, postalCode);
        if (fromCache.isPresent()) {
            return fromCache;
        } else {
            return findFromExternalService(country, postalCode);
        }
    }

    private Optional<PostalTO> findFromCache(final CountryEnum country, final String postalCode) {
        PostalEntity postalEntity = postalCodeDAO.findLastPostalCode(postalCode, country.name());
        if (postalEntity == null) {
            return Optional.empty();
        }
        final PostalTO postalTO = new PostalTO(
                postalEntity.getPostalCode(),
                postalEntity.getAddress(),
                postalEntity.getCity(),
                postalEntity.getState(),
                postalEntity.getCountry());
        return Optional.of(postalTO);
    }

    private Optional<PostalTO> findFromExternalService(final CountryEnum country, final String postalCode) {
        final Optional<PostalTO> postalTOOptional = externalPostalCodeFactory.findAdapter(country).find(postalCode);
        postalTOOptional.ifPresent(this::saveCache);
        return postalTOOptional;
    }

    private void saveCache(final PostalTO postalTO) {
        final PostalEntity postalEntity = new PostalEntity();
        postalEntity.setPostalCode(postalTO.postalCode());
        postalEntity.setCity(postalTO.city());
        postalEntity.setCountry(postalTO.country());
        postalEntity.setAddress(postalTO.address());
        postalEntity.setState(postalTO.state());
        postalCodeDAO.save(postalEntity);
    }
}
