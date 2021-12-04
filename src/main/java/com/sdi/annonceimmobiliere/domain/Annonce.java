package com.sdi.annonceimmobiliere.domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.sun.istack.NotNull;

@Entity
@Table(name = Annonce.TABLE_NAME)
@Access(AccessType.FIELD)
public class Annonce {
	public static final String TABLE_NAME = "annonce";
	public static final String TABLE_ID = TABLE_NAME + "_ID";
	private static final String TABLE_SEQ = TABLE_ID + "_SEQ";

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = TABLE_SEQ)
	@SequenceGenerator(name = TABLE_SEQ, sequenceName = TABLE_SEQ)
	private Long id;

	@NotNull
	@Column(name = "title", nullable = false)
	private String title;

	@NotNull
	@Column(name = "description", nullable = false)
	private String description;

	@NotNull
	@Column(name = "file_name", nullable = false)
	private String fileName;

	/**
	 * Default constructor.
	 */
	public Annonce() {
	}

	/**
	 * Constructor with parameter..
	 *
	 * @param title the title of the ad.
	 * @param description the description of the ad.
	 * @param fileName the name of the file of the ad.
	 */
	public Annonce(String title, String description, String fileName) {
		this.title = title;
		this.description = description;
		this.fileName = fileName;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
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
