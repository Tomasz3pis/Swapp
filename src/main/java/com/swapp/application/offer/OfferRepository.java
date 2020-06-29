package com.swapp.application.offer;

import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface OfferRepository extends CrudRepository<Offer, Long> {

    @Query("select offer from Offer offer where lower(offer.title) like lower(:titleToFind) AND offer.userId = :id")
    List<Offer> findByTitleIgnoreCaseAndUserId(@Param("titleToFind") String title, @Param("id") long id);

    List<Offer> findByUserId(long userId);

    Optional<Offer> findByIdAndUserId(long id, long userId);

    boolean existsByIdAndUserId(long offerId, long userId);
}
