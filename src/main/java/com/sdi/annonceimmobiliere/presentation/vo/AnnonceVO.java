package com.sdi.annonceimmobiliere.presentation.vo;
import javax.validation.constraints.NotNull;

import com.sdi.annonceimmobiliere.domain.Annonce;

public class AnnonceVO {
	private Long id;
	@NotNull
	private String title;
	@NotNull
	private String description;
	private String fileName;

	/**
	 * Default constructor.
	 */
	public AnnonceVO() {
	}

	/**
	 * Constructor with parameter.
	 *
	 * @param annonce the ads entity.
	 */
	public AnnonceVO(Annonce annonce) {
		this.id = annonce.getId();
		this.title = annonce.getTitle();
		this.description = annonce.getDescription();
		this.fileName = annonce.getFileName();
	}

	public Long getId() {
		return id;
	}

	public String getTitle() {
		return title;
	}

	public String getDescription() {
		return description;
	}

	public String getFileName() {
		return fileName;
	}
}
