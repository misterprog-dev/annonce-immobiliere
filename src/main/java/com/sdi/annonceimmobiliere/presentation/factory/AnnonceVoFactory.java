package com.sdi.annonceimmobiliere.presentation.factory;

import static java.util.stream.Collectors.toSet;

import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Service;

import com.sdi.annonceimmobiliere.domain.Annonce;
import com.sdi.annonceimmobiliere.presentation.vo.AnnonceVO;

/**
 * Declare the factory for annonce (ad) VO.
 */
@Service
public class AnnonceVoFactory {
	/**
	 * Convert a ad entity to VO.
	 *
	 * @param annonce the ads we want to convert in VO object.
	 * @return a ad VO.
	 */
	public AnnonceVO annonceVO(Annonce annonce) {
		return new AnnonceVO(annonce);
	}

	/**
	 * Convert a list of ads entities to list of VO.
	 *
	 * @param annonces the list of ads we want to convert in VO object.
	 * @return the list of VO of ads.
	 */
	public Set<AnnonceVO> annoncesVO(List<Annonce> annonces) {
		return annonces.stream()
				.map(AnnonceVO::new)
				.collect(toSet());
	}
}
