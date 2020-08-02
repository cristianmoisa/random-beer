package com.random.beer.app.repository;

import com.random.beer.app.model.Beer;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BeerRepository extends JpaRepository<Beer, Long> {

  Optional<Beer> findByBeerName(String beerName);

}
