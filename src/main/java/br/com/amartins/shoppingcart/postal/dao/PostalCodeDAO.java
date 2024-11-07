package br.com.amartins.shoppingcart.postal.dao;

import br.com.amartins.shoppingcart.postal.entity.PostalEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface PostalCodeDAO extends JpaRepository<PostalEntity, Long> {
    @Query("SELECT p FROM Postal p WHERE p.postalCode = :postalCode and p.country = :country order by createdDate desc limit 1")
    PostalEntity findLastPostalCode(String postalCode, String country);
}
