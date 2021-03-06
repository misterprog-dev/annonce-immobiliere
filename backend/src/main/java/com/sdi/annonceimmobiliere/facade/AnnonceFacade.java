package com.sdi.annonceimmobiliere.facade;

import javax.persistence.EntityNotFoundException;
import java.util.Objects;
import java.util.Set;

import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.sdi.annonceimmobiliere.domain.Annonce;
import com.sdi.annonceimmobiliere.presentation.factory.AnnonceVoFactory;
import com.sdi.annonceimmobiliere.presentation.vo.AnnonceVO;
import com.sdi.annonceimmobiliere.repository.AnnonceRepository;
import com.sdi.annonceimmobiliere.service.FileStorageService;

/**
 * Facade to manage all ads.
 */
@Service
public class AnnonceFacade {

	private final AnnonceRepository annonceRepository;
	private final AnnonceVoFactory annonceVoFactory;
	private final FileStorageService fileStorageService;

	public AnnonceFacade(AnnonceRepository annonceRepository,
						 AnnonceVoFactory annonceVoFactory,
						 FileStorageService fileStorageService) {
		this.annonceRepository = annonceRepository;
		this.annonceVoFactory = annonceVoFactory;
		this.fileStorageService = fileStorageService;
	}

	/**
	 * Read all ads.
	 *
	 * @return a Set of ads
	 */
	@Transactional(readOnly = true)
	public Set<AnnonceVO> readAds() {
		return annonceVoFactory.annoncesVO(annonceRepository.findAll());
	}

	/**
	 * Read one ad that have his id specified.
	 *
	 * @param id the id of ad.
	 * @return a ad.
	 */
	@Transactional(readOnly = true)
	public AnnonceVO readAd(Long id) {
		Annonce annonce = annonceRepository.findById(id)
				.orElseThrow(()-> new EntityNotFoundException("Entity not found"));
		return annonceVoFactory.annonceVO(annonce);
	}

	/**
	 * Delete one ad that have his id specified.
	 *
	 * @param id the id of ad.
	 */
	@Transactional
	public void deleteAd(Long id) {
		Annonce annonce = annonceRepository.findById(id)
				.orElseThrow(()-> new EntityNotFoundException("Entity not found"));

		annonceRepository.delete(annonce);

		// We store image.
		fileStorageService.delete(id.toString());
	}

	/**
	 * Save a ad.
	 *
	 * @param annonceVO ad information.
	 * @param image image of ad.
	 */
	@Transactional
	public void save(AnnonceVO annonceVO, MultipartFile image) {
		Annonce annonce = new Annonce();
		annonce.setTitle(annonceVO.getTitle());
		annonce.setDescription(annonceVO.getDescription());
		if (image != null) {
			annonce.setFileName(StringUtils.cleanPath(Objects.requireNonNull(image.getOriginalFilename())));
		}
		annonce = annonceRepository.save(annonce);
		// We store image.
		if (image != null) {
			fileStorageService.save(image, annonce.getId().toString(), false);
		}
	}

	/**
	 * Update a ad.
	 *
	 * @param id id of ad to update.
	 * @param annonceVO ad information.
	 * @param image image of ad.
	 */
	@Transactional
	public void update(Long id, AnnonceVO annonceVO, MultipartFile image) {
		Annonce annonce = annonceRepository.findById(id)
				.orElseThrow(()-> new EntityNotFoundException("Entity not found"));

		annonce.setTitle(annonceVO.getTitle());
		annonce.setDescription(annonceVO.getDescription());
		if (image != null) {
			annonce.setFileName(StringUtils.cleanPath(Objects.requireNonNull(image.getOriginalFilename())));
			// We update image.
			fileStorageService.save(image, annonce.getId().toString(), true);
		}
		else {
			// We delete image.
			annonce.setFileName(null);
			fileStorageService.delete(annonce.getId().toString());
		}
		annonceRepository.save(annonce);
	}

	/**
	 * Read a file of ad.
	 *
	 * @param folder the folder of file.
	 * @param fileName the file name.
	 * @return a file of ad.
	 */
	public Resource getFile(String folder, String fileName) {
		return fileStorageService.read(folder, fileName);
	}
}
