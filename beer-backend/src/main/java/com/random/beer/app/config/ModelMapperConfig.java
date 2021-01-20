package com.random.beer.app.config;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModelMapperConfig {

  private static final Logger log = LoggerFactory.getLogger(ModelMapperConfig.class);

  @Bean
  public ModelMapper modelMapper() {
    log.info("Create modelMapper mapper implementation bean");
    return new ModelMapper();
  }
}
