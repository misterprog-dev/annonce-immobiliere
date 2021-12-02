package com.sdi.annonceimmobiliere.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sdi.annonceimmobiliere.domain.Annonce;

/**
 * Repository of ads
 */
@Repository
public interface AnnonceRepository extends JpaRepository<Annonce, Long> {

}
