package com.sdi.annonceimmobiliere;

import javax.annotation.Resource;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

import com.sdi.annonceimmobiliere.service.FileStorageService;

@SpringBootApplication
@EntityScan(basePackages = {"com.sdi.annonceimmobiliere.domain"})
public class AnnonceImmobilireApplication implements CommandLineRunner {

	@Resource
	FileStorageService fileStorageService;

	public static void main(String[] args) {
		SpringApplication.run(AnnonceImmobilireApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		fileStorageService.init();
	}
}
