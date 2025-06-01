package com.bkalbitz.bandcamp_item_detail;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan("com.bkalbitz.bandcamp_item_detail")
@SpringBootApplication
public class BandcampItemDetailApplication {

  public static void main(String[] args) {
    SpringApplication.run(BandcampItemDetailApplication.class, args);
  }

}
