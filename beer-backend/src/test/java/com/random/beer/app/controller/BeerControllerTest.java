package com.random.beer.app.controller;

import com.random.beer.app.model.Beer;
import com.random.beer.app.service.BeerService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.zalando.problem.ProblemModule;
import org.zalando.problem.violations.ConstraintViolationProblemModule;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.CoreMatchers.is;


@WebMvcTest(controllers = BeerController.class)
@ActiveProfiles("test")
public class BeerControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private BeerService beerService;

  @Autowired
  private ObjectMapper objectMapper;

  private List<Beer> beerList;

  @BeforeEach
  void setUp() {
    this.beerList = new ArrayList<>();
    this.beerList.add(new Beer(1L, "Ale", "One of the best beers in the world", 3.5, "U.S.A",
        "https://ichef.bbci.co.uk/news/1024/cpsprodpb/61BE/production/_111222052_beerglasses.jpg"));
    this.beerList.add(new Beer(2L, "Radler", "Like lemonade with beer", 1.5, "Germany",
        "https://trulyexperiences.com/blog/wp-content/uploads/2014/11/featured_0002_Five-Unique-Beers-You-Must-Try.jpg"));
    this.beerList.add(new Beer(3L, "Budweiser", "American beer, not the best", 5.5, "U.S.A",
        "https://ichef.bbci.co.uk/news/1024/cpsprodpb/61BE/production/_111222052_beerglasses.jpg"));
    this.beerList.add(new Beer(4L, "Guinness", "Overpriced irish beer. Not so bad", 5.0, "Ireland",
        "https://i0.wp.com/post.healthline.com/wp-content/uploads/2020/02/beer-bar-1296x728-header.jpg?w=1155&h=1528"));
    this.beerList.add(new Beer(5L, "Heineken", "Some average EU beer", 4.5, "E.U.",
        "https://www.connshg.com/Resources/b5f10bc2-4cd8-4ccf-be25-d8b538cf524c/bigstock-Beer-Cold-Craft-light-Beer-in-202781995.jpg"));

    objectMapper.registerModule(new ProblemModule());
    objectMapper.registerModule(new ConstraintViolationProblemModule());
  }

  @Test
  void getAllBeers() throws Exception {

    given(beerService.findAllBeers()).willReturn(beerList);

    this.mockMvc.perform(get("/api/beers"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.size()", is(beerList.size())));
  }

  @Test
  void getRandomBeer() throws Exception {
    final Beer beer = new Beer(1L, "Ginger Ale", "Tastes funny", 3.5, "U.S.A",
        "https://www.connshg.com/Resources/b5f10bc2-4cd8-4ccf-be25-d8b538cf524c/bigstock-Beer-Cold-Craft-light-Beer-in-202781995.jpg");
    given(beerService.getRandomBeer()).willReturn(beer);
    this.mockMvc.perform(get("/api/beers/random"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.beerName", is(beer.getBeerName())))
        .andExpect(jsonPath("$.description", is(beer.getDescription())))
        .andExpect(jsonPath("$.alcoholPercentage", is(beer.getAlcoholPercentage())))
        .andExpect(jsonPath("$.breweryLocation", is(beer.getBreweryLocation())))
        .andExpect(jsonPath("$.beerImageUrl", is(beer.getBeerImageUrl())));
  }

  @Test
  void getBeerById() throws Exception {
    final Long beerId = 1L;
    final Beer beer = new Beer(1L, "Ginger Ale", "Tastes funny", 3.5, "U.S.A", ""
        + "https://www.connshg.com/Resources/b5f10bc2-4cd8-4ccf-be25-d8b538cf524c/bigstock-Beer-Cold-Craft-light-Beer-in-202781995.jpg");

    given(beerService.findBeerById(beerId)).willReturn(Optional.of(beer));

    this.mockMvc.perform(get("/api/beers/{id}", beerId))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.beerName", is(beer.getBeerName())))
        .andExpect(jsonPath("$.description", is(beer.getDescription())))
        .andExpect(jsonPath("$.alcoholPercentage", is(beer.getAlcoholPercentage())))
        .andExpect(jsonPath("$.breweryLocation", is(beer.getBreweryLocation())))
        .andExpect(jsonPath("$.beerImageUrl", is(beer.getBeerImageUrl())));
  }

  @Test
  void getBeerById_returns404() throws Exception {
    final Long beerId = 1L;
    given(beerService.findBeerById(beerId)).willReturn(Optional.empty());

    this.mockMvc.perform(get("/api/beer/{id}", beerId))
        .andExpect(status().isNotFound());
  }

  @Test
  void createBeer() throws Exception {
    given(beerService.createBeer(any(Beer.class))).willAnswer((invocation) -> invocation.getArgument(0));

    Beer beer = new Beer(null, "Ginger Ale", "Tastes funny", 3.5, "U.S.A", ""
        + "https://www.connshg.com/Resources/b5f10bc2-4cd8-4ccf-be25-d8b538cf524c/bigstock-Beer-Cold-Craft-light-Beer-in-202781995.jpg");

    this.mockMvc.perform(post("/api/beers")
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(beer)))
        .andExpect(status().isCreated())
        .andExpect(jsonPath("$.beerName", is(beer.getBeerName())))
        .andExpect(jsonPath("$.description", is(beer.getDescription())))
        .andExpect(jsonPath("$.alcoholPercentage", is(beer.getAlcoholPercentage())))
        .andExpect(jsonPath("$.breweryLocation", is(beer.getBreweryLocation())))
        .andExpect(jsonPath("$.beerImageUrl", is(beer.getBeerImageUrl())));
  }

  @Test
  void createBeer_withoutName_returns400() throws Exception {
    Beer beer = new Beer(null, null, "Tastes funny", 3.5, "U.S.A", ""
        + "https://www.connshg.com/Resources/b5f10bc2-4cd8-4ccf-be25-d8b538cf524c/bigstock-Beer-Cold-Craft-light-Beer-in-202781995.jpg");

    this.mockMvc.perform(post("/api/beers")
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(beer)))
        .andExpect(status().isBadRequest())
        .andReturn()
    ;
  }

  @Test
  void updateBeer() throws Exception {
    Long beerId = 1L;
    Beer beer = new Beer(beerId, "Ginger Ale", "Tastes funny", 3.5, "U.S.A",
        "https://www.connshg.com/Resources/b5f10bc2-4cd8-4ccf-be25-d8b538cf524c/bigstock-Beer-Cold-Craft-light-Beer-in-202781995.jpg");
    given(beerService.findBeerById(beerId)).willReturn(Optional.of(beer));
    given(beerService.updateBeer(any(Beer.class))).willAnswer((invocation) -> invocation.getArgument(0));

    this.mockMvc.perform(put("/api/beers/{id}", beer.getId())
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(beer)))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.beerName", is(beer.getBeerName())))
        .andExpect(jsonPath("$.description", is(beer.getDescription())))
        .andExpect(jsonPath("$.alcoholPercentage", is(beer.getAlcoholPercentage())))
        .andExpect(jsonPath("$.breweryLocation", is(beer.getBreweryLocation())))
        .andExpect(jsonPath("$.beerImageUrl", is(beer.getBeerImageUrl())));
  }

  @Test
  void updateBeer_nonExisting_return404() throws Exception {
    Long beerId = 1L;
    given(beerService.findBeerById(beerId)).willReturn(Optional.empty());
    Beer beer = new Beer(beerId, "Ginger Ale", "Tastes funny", 3.5, "U.S.A",
        "https://www.connshg.com/Resources/b5f10bc2-4cd8-4ccf-be25-d8b538cf524c/bigstock-Beer-Cold-Craft-light-Beer-in-202781995.jpg");

    this.mockMvc.perform(put("/api/beers/{id}", beerId)
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(beer)))
        .andExpect(status().isNotFound());

  }

  @Test
  void deleteBeer() throws Exception {
    Long beerId = 1L;
    Beer beer = new Beer(beerId, "Ginger Ale", "Tastes funny", 3.5, "U.S.A",
        "https://www.connshg.com/Resources/b5f10bc2-4cd8-4ccf-be25-d8b538cf524c/bigstock-Beer-Cold-Craft-light-Beer-in-202781995.jpg");
    given(beerService.findBeerById(beerId)).willReturn(Optional.of(beer));
    doNothing().when(beerService).deleteBeerById(beer.getId());

    this.mockMvc.perform(delete("/api/beers/{id}", beer.getId()))
        .andExpect(status().isOk());

  }

}