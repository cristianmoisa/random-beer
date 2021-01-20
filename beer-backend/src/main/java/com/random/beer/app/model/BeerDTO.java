package com.random.beer.app.model;

import javax.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BeerDTO {

  private Long id;
  @NotEmpty
  private String beerName;
  private String description;
  private double alcoholPercentage;
  private String breweryLocation;
  private String beerImageUrl;
  private boolean isDeleted;
}
