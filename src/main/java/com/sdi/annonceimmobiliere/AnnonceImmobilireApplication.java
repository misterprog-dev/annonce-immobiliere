package com.sdi.annonceimmobiliere;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@EntityScan(basePackages = {"com.sdi.annonceimmobiliere.domain"})
public class AnnonceImmobilireApplication {

	public static void main(String[] args) {
		SpringApplication.run(AnnonceImmobilireApplication.class, args);
	}

}
