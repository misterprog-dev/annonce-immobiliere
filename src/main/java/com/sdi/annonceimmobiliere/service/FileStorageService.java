package com.sdi.annonceimmobiliere.service;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

public interface FileStorageService {

	void init();

	/**
	 * Save a file.
	 *
	 * @param file the file to upload.
	 * @param folder the folder to store.
	 * @param isUpdate flag to check if is updating.
	 */
	void save(MultipartFile file, String folder, boolean isUpdate);

	/**
	 * Delete a folder.
	 *
	 * @param folder the folder to delete.
	 */
	void delete(String folder);

	/**
	 * Load a file
	 *
	 * @param folder the folder of file.
	 * @param fileName file name
	 * @return file
	 */
	Resource read(String folder, String fileName);
}
