package com.bkalbitz.bandcamp_collection_explorer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;



@SpringBootApplication
@EnableAutoConfiguration
@ComponentScan("com.bkalbitz.bandcamp_collection_explorer")
public class BandcampCollectionExplorerApplication {

	public static void main(String[] args) {
		SpringApplication.run(BandcampCollectionExplorerApplication.class, args);
	}

	
}
