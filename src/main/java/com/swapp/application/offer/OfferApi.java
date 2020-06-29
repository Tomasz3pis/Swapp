package com.swapp.application.offer;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@RequestMapping("offers")
@CrossOrigin
public interface OfferApi {

    @GetMapping(value = "/{offerId}")
    ResponseEntity<Offer> getOfferById(@PathVariable long offerId);

    @GetMapping
    ResponseEntity<List<Offer>> getOffers();

    @PostMapping
    ResponseEntity<?> addOffer(Offer offer);

    @PutMapping(value = "/{offerId}")
    ResponseEntity<?> updateOffer(@PathVariable long offerId, Offer offer);

    @DeleteMapping(value = "/{offerId}")
    ResponseEntity<?> deleteOffer(@PathVariable long offerId);

}
