package com.random.beer.app.api.service;

import static org.mockito.BDDMockito.given;

import com.random.beer.app.model.Beer;
import com.random.beer.app.model.BeerDTO;
import com.random.beer.app.repository.BeerRepository;
import com.random.beer.app.service.BeerService;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
public class BeerServiceTest {

  @Mock
  private BeerRepository beerRepository;

  @InjectMocks
  private BeerService beerService;

  private ModelMapper modelMapper;

//  @Test
//  void saveBeer() {
//    final Beer beer = new Beer(1L, "Ginger Ale", "Tastes funny", 3.5, "U.S.A",
//        "https://www.connshg.com/Resources/b5f10bc2-4cd8-4ccf-be25-d8b538cf524c/bigstock-Beer-Cold-Craft-light-Beer-in-202781995.jpg");
//
//    given(beerRepository.findByBeerName(beer.getBeerName())).willReturn(Optional.empty());
//    given(beerRepository.save(beer)).willAnswer(invocation -> invocation.getArgument(0));
//
//    Beer savedBeer = beerService.createBeer(beer);
//
//    assertThat(savedBeer).isNotNull();
//    verify(beerRepository).save(any(Beer.class));
//  }
//
//  @Test
//  void saveBeer_nameExists() {
//    final Beer beer = new Beer(1L, "Ginger Ale", "Tastes funny", 3.5, "U.S.A",
//        "https://www.connshg.com/Resources/b5f10bc2-4cd8-4ccf-be25-d8b538cf524c/bigstock-Beer-Cold-Craft-light-Beer-in-202781995.jpg");
//
//    given(beerRepository.findByBeerName(beer.getBeerName())).willReturn(Optional.of(beer));
//
//    assertThrows(BeerExistsException.class,() -> {
//      beerService.createBeer(beer);
//    });
//
//    verify(beerRepository, never()).save(any(Beer.class));
//  }

//  @Test
//  void updateBeer() {
//    final Beer beer = new Beer(1L, "Ginger Ale", "Tastes funny", 3.5, "U.S.A",
//        "https://www.connshg.com/Resources/b5f10bc2-4cd8-4ccf-be25-d8b538cf524c/bigstock-Beer-Cold-Craft-light-Beer-in-202781995.jpg");
//
//    given(beerRepository.save(beer)).willReturn(beer);
//
//    final Beer expected = beerService.updateBeer(beer);
//
//    assertThat(expected).isNotNull();
//
//    verify(beerRepository).save(any(Beer.class));
//  }

  @Test
  void findAllBeers() {
    List<Beer> beers = new ArrayList();
    beers.add(new Beer(1L, "Ginger Ale", "Tastes funny", 3.5, "U.S.A",
        "https://www.connshg.com/Resources/b5f10bc2-4cd8-4ccf-be25-d8b538cf524c/bigstock-Beer-Cold-Craft-light-Beer-in-202781995.jpg", false));
    beers.add(new Beer(2L, "Ginger Ale2", "Tastes funny", 3.5, "U.S.A",
        "https://www.connshg.com/Resources/b5f10bc2-4cd8-4ccf-be25-d8b538cf524c/bigstock-Beer-Cold-Craft-light-Beer-in-202781995.jpg", false));
    beers.add(new Beer(3L, "Ginger Ale3", "Tastes funny", 3.5, "U.S.A",
        "https://www.connshg.com/Resources/b5f10bc2-4cd8-4ccf-be25-d8b538cf524c/bigstock-Beer-Cold-Craft-light-Beer-in-202781995.jpg", false));

    BeerDTO beerDTOS = modelMapper.map(beers, BeerDTO.class);
    given(beerRepository.findAll()).willReturn(beers);

    List<BeerDTO> expected = beerService.findAllBeers();

    assertEquals(expected, beers);
  }

//  @Test
//  void findBeerById(){
//    final Long id = 1L;
//    final Beer beer = new Beer(1L, "Ginger Ale", "Tastes funny", 3.5, "U.S.A",
//        "https://www.connshg.com/Resources/b5f10bc2-4cd8-4ccf-be25-d8b538cf524c/bigstock-Beer-Cold-Craft-light-Beer-in-202781995.jpg");
//
//    given(beerRepository.findById(id)).willReturn(Optional.of(beer));
//
//    final Optional<Beer> expected = beerService.findBeerById(id);
//
//    assertThat(expected).isNotNull();
//  }
//
//  @Test
//  void deleteBeerById() {
//    final Long beerId=1L;
//
//    beerService.deleteBeerById(beerId);
//    beerService.deleteBeerById(beerId);
//
//    verify(beerRepository, times(2)).deleteById(beerId);
//  }

}