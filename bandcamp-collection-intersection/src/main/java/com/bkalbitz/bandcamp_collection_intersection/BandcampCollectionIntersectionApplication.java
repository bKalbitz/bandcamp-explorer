package com.bkalbitz.bandcamp_collection_intersection;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EnableAutoConfiguration
@ComponentScan("com.bkalbitz.bandcamp_collection_intersection")
public class BandcampCollectionIntersectionApplication {

  public static void main(String[] args) {
    SpringApplication.run(BandcampCollectionIntersectionApplication.class, args);
  }

}
