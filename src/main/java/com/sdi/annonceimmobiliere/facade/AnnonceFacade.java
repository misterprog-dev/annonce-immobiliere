package com.sdi.annonceimmobiliere.facade;

import javax.persistence.EntityNotFoundException;
import java.io.IOException;
import java.util.Objects;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.sdi.annonceimmobiliere.domain.Annonce;
import com.sdi.annonceimmobiliere.presentation.factory.AnnonceVoFactory;
import com.sdi.annonceimmobiliere.presentation.vo.AnnonceVO;
import com.sdi.annonceimmobiliere.repository.AnnonceRepository;
import com.sdi.annonceimmobiliere.service.FileUploadService;

/**
 * Facade to manage all ads.
 */
@Service
public class AnnonceFacade {
	private static final String uploadDir = "upload/";
	private static final Logger logger = LoggerFactory.getLogger(AnnonceFacade.class);

	private final AnnonceRepository annonceRepository;
	private final AnnonceVoFactory annonceVoFactory;
	private final FileUploadService fileUploadService;

	public AnnonceFacade(AnnonceRepository annonceRepository,
						 AnnonceVoFactory annonceVoFactory,
						 FileUploadService fileUploadService) {
		this.annonceRepository = annonceRepository;
		this.annonceVoFactory = annonceVoFactory;
		this.fileUploadService = fileUploadService;
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
				.orElse(null);

		if (annonce == null) {
			throw new EntityNotFoundException("Entity not found");
		}

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
				.orElse(null);

		if (annonce == null) {
			throw new EntityNotFoundException("Entity not found");
		}

		annonceRepository.delete(annonce);
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

		String fileName = null;
		// We generate a unique name for image
		if (image != null) {
			fileName = StringUtils.cleanPath(Objects.requireNonNull(image.getOriginalFilename()));
			String extension = fileName.substring(fileName.lastIndexOf(".") + 1);

			annonce = annonceRepository.save(annonce);
			// Final name
			fileName = annonce.getId() + extension;

			try {
				// We save file in upload dir.
				String imageDir = uploadDir + fileName;
				fileUploadService.saveFile(imageDir, fileName, image);
			}
			catch (IOException e) {
				logger.info(e.getMessage());
			}
		}

		annonce.setFileName(fileName);
		annonceRepository.save(annonce);
	}

	/**
	 * Update a ad.
	 *
	 * @param id id of ad to update.
	 * @param annonceVO ad information.
	 * @param image image of ad.
	 */
	public void update(Long id, AnnonceVO annonceVO, MultipartFile image) {
		Annonce annonce = annonceRepository.findById(id)
				.orElse(null);

		if (annonce == null) {
			throw new EntityNotFoundException("Entity not found");
		}

		annonce.setTitle(annonceVO.getTitle());
		annonce.setDescription(annonceVO.getDescription());

		String fileName = annonce.getFileName();

		// We check if the ad had a image
		if (fileName == null) {
			fileName = StringUtils.cleanPath(Objects.requireNonNull(image.getOriginalFilename()));
			String extension = fileName.substring(fileName.lastIndexOf(".") + 1);

			// Final name
			fileName = annonce.getId() + extension;
		}

		try {
			// We save file in upload dir.
			String imageDir = uploadDir + fileName;
			fileUploadService.saveFile(imageDir, fileName, image);
		}
		catch (IOException e) {
			logger.info(e.getMessage());
		}

		annonce.setFileName(fileName);
		annonceRepository.save(annonce);
	}
}
