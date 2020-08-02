package com.random.beer.app.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class BeerExistsException extends RuntimeException {

  public BeerExistsException(String message) {
    super(message);
  }
}

