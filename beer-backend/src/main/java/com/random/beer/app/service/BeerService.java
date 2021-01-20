package com.random.beer.app.service;

import com.random.beer.app.exception.BeerExistsException;
import com.random.beer.app.model.Beer;
import com.random.beer.app.model.BeerDTO;
import com.random.beer.app.repository.BeerRepository;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class BeerService {

  private final BeerRepository beerRepository;
  private final ModelMapper modelMapper;

  @Autowired
  public BeerService(BeerRepository beerRepository, ModelMapper modelMapper) {
    this.beerRepository = beerRepository;
    this.modelMapper = modelMapper;
  }

  public List<BeerDTO> findAllBeers() {
    return beerRepository.findAll()
        .stream()
        .map(this::convertToBeerDTO)
        .collect(Collectors.toList());
  }

  public BeerDTO getRandomBeer() {
    Beer beer = beerRepository.findRandomBeer();
    return convertToBeerDTO(beer);
  }

  public ResponseEntity<BeerDTO> findBeerById(Long id) {
    return beerRepository.findById(id)
        .map(this::convertToBeerDTO)
        .map(ResponseEntity::ok)
        .orElseGet(() -> ResponseEntity.notFound().build());
  }

  public BeerDTO createBeer(BeerDTO beerDTO) {
    Beer beer = convertFromBeerDTO(beerDTO);
    Optional<Beer> dbBeer = beerRepository.findByBeerName(beer.getBeerName());
    if(dbBeer.isPresent()) {
      throw new BeerExistsException("Beer with name "+ beer.getBeerName()+" already exists");
    }
    Beer savedBeer = beerRepository.save(beer);
    return convertToBeerDTO(savedBeer);
  }

  public ResponseEntity<BeerDTO> updateBeer(Long id, BeerDTO beerDTO) {
    Beer beer = convertFromBeerDTO(beerDTO);
    beer.setId(id);
    Optional<Beer> dbBeer = beerRepository.findById(beer.getId());
    if(dbBeer.isPresent()) {
      Beer savedBeer = beerRepository.save(beer);
      return ResponseEntity.ok(convertToBeerDTO(savedBeer));
    } else {
      return ResponseEntity.notFound().build();
    }
  }

  public ResponseEntity deleteBeerById(Long id) {
    Optional<Beer> dbBeer = beerRepository.findById(id);
    if(dbBeer.isPresent()) {
      Beer beer = dbBeer.get();
      beer.setDeleted(true);
      beerRepository.save(beer);
      return ResponseEntity.ok(id);
    } else {
      return ResponseEntity.notFound().build();
    }
  }

  private BeerDTO convertToBeerDTO(Beer beer) {
    return modelMapper.map(beer, BeerDTO.class);
  }

  private Beer convertFromBeerDTO(BeerDTO beerdTO) {
    return modelMapper.map(beerdTO, Beer.class);
  }

}
