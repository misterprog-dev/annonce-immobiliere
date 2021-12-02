package com.sdi.annonceimmobiliere.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.sdi.annonceimmobiliere.domain.Annonce;

@Repository
public interface AnnonceRepository extends CrudRepository<Annonce, Long> {

}
