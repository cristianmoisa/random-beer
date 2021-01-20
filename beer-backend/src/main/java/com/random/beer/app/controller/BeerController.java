package com.random.beer.app.controller;

import com.random.beer.app.model.BeerDTO;
import com.random.beer.app.service.BeerService;
import java.util.List;
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

  private final BeerService beerService;

  @Autowired
  public BeerController(BeerService beerService) {
    this.beerService = beerService;
  }

  @GetMapping()
  public List<BeerDTO> getAllBeers() {
    log.info("Request to get all beers");
    return beerService.findAllBeers();
  }

  @GetMapping("/random")
  public BeerDTO getRandomBeer() {
    log.info("Request to get a random beer.");
    return beerService.getRandomBeer();
  }

  @GetMapping("/{id}")
  public ResponseEntity<BeerDTO> getBeerById(@PathVariable Long id) {
    log.info("Request to get beer with id {}", id);
    return beerService.findBeerById(id);
  }

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public BeerDTO createBeer(@RequestBody @Validated BeerDTO beerDTO) {
    log.info("Request to create beer: {}", beerDTO);
    return beerService.createBeer(beerDTO);
  }

  @PutMapping("/{id}")
  public ResponseEntity<BeerDTO> updateBeer(@PathVariable Long id, @RequestBody BeerDTO beerDTO) {
    log.info("Request to update beer with id {}", id);
    return beerService.updateBeer(id, beerDTO);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity deleteBeer(@PathVariable Long id) {
    log.info("Request to delete beer with id {}", id);
    return beerService.deleteBeerById(id);
  }

}