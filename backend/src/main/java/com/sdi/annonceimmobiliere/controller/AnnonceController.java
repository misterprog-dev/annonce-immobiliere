package com.sdi.annonceimmobiliere.controller;

import static org.apache.tomcat.util.http.fileupload.FileUploadBase.MULTIPART_FORM_DATA;

import javax.validation.Valid;
import java.util.Set;

import org.springframework.core.io.Resource;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.sdi.annonceimmobiliere.facade.AnnonceFacade;
import com.sdi.annonceimmobiliere.presentation.vo.AnnonceVO;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping(value = "/api/annonces")
public class AnnonceController {
	private final AnnonceFacade annonceFacade;

	public AnnonceController(AnnonceFacade annonceFacade) {
		this.annonceFacade = annonceFacade;
	}

	/**
	 * Read all ads registered.
	 * @return a set of ads.
	 */
	@GetMapping
	public Set<AnnonceVO> readAds() {
		return annonceFacade.readAds();
	}

	/**
	 * Read one ad that have his id specified.
	 * @param id the id of ad.
	 * @return a ad.
	 */
	@GetMapping("{id}")
	public AnnonceVO readAd(@PathVariable Long id) {
		return annonceFacade.readAd(id);
	}

	/**
	 * Delete one ad that have his id specified.
	 * @param id the id of ad.
	 */
	@DeleteMapping("{id}")
	public void deleteAd(@PathVariable Long id) {
		annonceFacade.deleteAd(id);
	}

	/**
	 * Create a ad.
	 *
	 * @param annonceVO ad information.
	 * @param image image of ad.
	 */
	@PostMapping(consumes = MULTIPART_FORM_DATA)
	public void save(@RequestPart("annonce") @Valid AnnonceVO annonceVO,
					 @RequestPart(name="image", required= false) MultipartFile image) {
		annonceFacade.save(annonceVO, image);
	}

	/**
	 * Update a ad.
	 *
	 * @param id id of ad to update.
	 * @param annonceVO ad information.
	 * @param image image of ad.
	 */
	@PutMapping(value = "{id}", consumes = MULTIPART_FORM_DATA)
	public void update(@PathVariable Long id,
					   @RequestPart("annonce") @Valid AnnonceVO annonceVO,
					   @RequestPart(name="image", required= false) MultipartFile image) {
		annonceFacade.update(id, annonceVO, image);
	}

	/**
	 * Read a file of ad.
	 *
	 * @param folder the folder of file.
	 * @param fileName the file name.
	 * @return a file of ad.
	 */
	@GetMapping(value = "{folder}/{fileName}/file")
	public Resource getFile(@PathVariable String folder, @PathVariable String fileName) {
		return annonceFacade.getFile(folder, fileName);
	}
}
