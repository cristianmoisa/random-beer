package com.random.beer.app.api.repository;

import static org.assertj.core.api.Assertions.assertThat;

import com.random.beer.app.model.Beer;
import com.random.beer.app.repository.BeerRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javax.sql.DataSource;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

@Testcontainers
@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class BeerRepositoryTest {

  @Container
  private static final PostgreSQLContainer POSTGRESQL_CONTAINER = new PostgreSQLContainer("postgres:11.8")
      .withDatabaseName("beerdbz")
      .withUsername("postgres")
      .withPassword("password");

  @TestConfiguration
  static class UserRepositoryTestConfiguration {
    @Bean
    public DataSource dataSource() {
      return DataSourceBuilder.create()
          .username(POSTGRESQL_CONTAINER.getUsername())
          .password(POSTGRESQL_CONTAINER.getPassword())
          .driverClassName(POSTGRESQL_CONTAINER.getDriverClassName())
          .url(POSTGRESQL_CONTAINER.getJdbcUrl())
          .build();
    }
  }

  @Autowired
  private BeerRepository beerRepository;

  @Test
  public void findBeerByName() {
    Beer beer = new Beer(6L, "Cold beer", "One of the best beers in the world", 3.5, "U.S.A",
        "https://ichef.bbci.co.uk/news/1024/cpsprodpb/61BE/production/_111222052_beerglasses.jpg", false);
    beerRepository.save(beer);
    assertThat(beerRepository.findByBeerName(beer.getBeerName()))
        .isNotNull()
        .isEqualTo(Optional.of(beer));
  }

  @Test
  public void findRandomBeer() {
    List<Beer> beerList = new ArrayList<>();
    beerList.add(new Beer(1L, "Budweiser", "The best american beer ever", 5.0, "U.S.A",
        "https://ichef.bbci.co.uk/news/1024/cpsprodpb/61BE/production/_111222052_beerglasses.jpg", false));
    beerList.add(new Beer(2L, "Ale", "British beer", 5.9, "U.K",
        "https://trulyexperiences.com/blog/wp-content/uploads/2014/11/featured_0002_Five-Unique-Beers-You-Must-Try.jpg", false));
    beerList.add(new Beer(3L, "Raddler", "Like lemonade", 2.5, "Germany",
        "https://ichef.bbci.co.uk/news/1024/cpsprodpb/61BE/production/_111222052_beerglasses.jpg", false));
    beerList.add(new Beer(4L, "Guinness", "Overpriced irish beer", 7.0, "Ireland",
        "https://i0.wp.com/post.healthline.com/wp-content/uploads/2020/02/beer-bar-1296x728-header.jpg?w=1155&h=1528", false));
    beerList.add(new Beer(5L, "Heineken", "Very popular beer", 5.0, "E.U.",
        "https://www.connshg.com/Resources/b5f10bc2-4cd8-4ccf-be25-d8b538cf524c/bigstock-Beer-Cold-Craft-light-Beer-in-202781995.jpg", false));
    assertThat(beerRepository.findRandomBeer())
        .isNotNull()
        .isIn(beerList);
  }
}
