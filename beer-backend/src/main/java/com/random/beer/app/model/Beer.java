package com.random.beer.app.model;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "beers")
public class Beer {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO, generator = "generator")
  @SequenceGenerator(name = "generator", sequenceName = "beers_seq", allocationSize = 1)
  @Column(name = "id", nullable = false, updatable = false)
  private Long id;

  @NotEmpty()
  @Column(nullable = false, unique = true)
  private String beerName;

  @Column()
  private String description;

  @Column
  private double alcoholPercentage;

  @Column()
  private String breweryLocation;

  @Column()
  private String beerImageUrl;

}
