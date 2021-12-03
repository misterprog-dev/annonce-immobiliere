package com.sdi.annonceimmobiliere;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class AnnonceImmobilireApplication {

	public static void main(String[] args) {
		SpringApplication.run(AnnonceImmobilireApplication.class, args);
	}
}
