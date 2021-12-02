package com.sdi.annonceimmobiliere;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class, HibernateJpaAutoConfiguration.class})
@EntityScan(basePackages = {"com.sdi.annonceimmobiliere.domain"})
public class AnnonceImmobilireApplication {

	public static void main(String[] args) {
		SpringApplication.run(AnnonceImmobilireApplication.class, args);
	}
}
