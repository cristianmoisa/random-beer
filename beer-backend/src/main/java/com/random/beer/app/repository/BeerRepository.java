package com.random.beer.app.repository;

import com.random.beer.app.model.Beer;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface BeerRepository extends JpaRepository<Beer, Long> {

  Optional<Beer> findByBeerName(String beerName);

  @Query(value = " SELECT r.* "
      + "        FROM ( "
      + "            SELECT FLOOR(mm.min_id + (mm.max_id - mm.min_id + 1) * RANDOM()) AS id "
      + "                FROM ( "
      + "                    SELECT MIN(id) AS min_id, "
      + "                           MAX(id) AS max_id "
      + "                        FROM beers "
      + "                     ) AS mm "
      + "             ) AS init "
      + "        JOIN  beers AS r  ON r.id = init.id;", nativeQuery = true)
  Beer findRandomBeer();

}
