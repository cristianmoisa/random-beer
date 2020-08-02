package com.random.beer.app.service;

import com.random.beer.app.exception.BeerExistsException;
import com.random.beer.app.model.Beer;
import com.random.beer.app.repository.BeerRepository;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class BeerService {

  private BeerRepository beerRepository;

  @Autowired
  public BeerService(BeerRepository beerRepository) {
    this.beerRepository = beerRepository;
  }

  public Beer createBeer(Beer beer) {
    Optional<Beer> dbBeer = beerRepository.findByBeerName(beer.getBeerName());
    if(dbBeer.isPresent()) {
      throw new BeerExistsException("Beer with name "+ beer.getBeerName()+" already exists");
    }
    return beerRepository.save(beer);
  }
  
  public Beer getRandomBeer() {
    List<Beer> results = findAllBeers();
    Random random = new Random();
    return results.get(random.nextInt(results.size()));
  }

  public Beer updateBeer(Beer beer) {
    return beerRepository.save(beer);
  }

  public List<Beer> findAllBeers() {
    return beerRepository.findAll();
  }

  public Optional<Beer> findBeerById(Long id) {
    return beerRepository.findById(id);
  }

  public void deleteBeerById(Long id) {
    beerRepository.deleteById(id);
  }

}
