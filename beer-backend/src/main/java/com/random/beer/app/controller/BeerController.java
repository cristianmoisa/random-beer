package com.random.beer.app.controller;

import com.random.beer.app.model.Beer;
import com.random.beer.app.service.BeerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/api/beers")
@Slf4j
public class BeerController {

  private BeerService beerService;

  @Autowired
  public BeerController(BeerService beerService) {
    this.beerService = beerService;
  }

  @GetMapping()
  public Iterable<Beer> getAllBeers() {
    return beerService.findAllBeers();
  }

  @GetMapping("/{id}")
  public ResponseEntity<Beer> getBeerById(@PathVariable Long id) {
    log.info("Request to get beer with id {}", id);
    return beerService.findBeerById(id)
        .map(ResponseEntity::ok)
        .orElseGet(() -> ResponseEntity.notFound().build());
  }

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public Beer createBeer(@RequestBody @Validated Beer beer) {
    log.info("Request to create beer: {}", beer);
    return beerService.createBeer(beer);
  }

  @PutMapping("/{id}")
  public ResponseEntity<Beer> updateBeer(@PathVariable Long id, @RequestBody Beer beer) {
    log.info("Request to update beer with id {}", id);
    return beerService.findBeerById(id)
        .map(beerObj -> {
          beerObj.setId(id);
          return ResponseEntity.ok(beerService.updateBeer(beerObj));
        })
        .orElseGet(() -> ResponseEntity.notFound().build());
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Beer> deleteBeer(@PathVariable Long id) {
    log.info("Request to delete beer with id {}", id);
    return beerService.findBeerById(id)
        .map(beer -> {
          beerService.deleteBeerById(id);
          return ResponseEntity.ok(beer);
        })
        .orElseGet(() -> ResponseEntity.notFound().build());
  }

  @GetMapping("/random")
  public Beer getRandomBeer() {
    log.info("Request to get a random beer.");
    return beerService.getRandomBeer();
  }

}