package com.sdi.annonceimmobiliere.service;

import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.multipart.MultipartFile;

@Service
public class FileStorageServiceImpl implements FileStorageService {
	private final Path root = Paths.get("frontend/src/assets/upload");

	@Override
	public void init() {
		try {
			// Check if folder don't exists.
			if (!Files.exists(root)){
				Files.createDirectories(root);
			}
		} catch (IOException e) {
			throw new RuntimeException("Could not create upload directory.");
		}
	}

	@Override
	public void save(MultipartFile file, String folder, boolean isUpdate) {
		try {
			String imageDir = root + "/" + folder;
			Path uploadPath = Paths.get(imageDir);

			if (isUpdate) {
				FileSystemUtils.deleteRecursively(uploadPath);
			}

			if (!Files.exists(uploadPath)) {
				Files.createDirectories(uploadPath);
			}

			Path filePath = uploadPath.resolve(Objects.requireNonNull(file.getOriginalFilename()));
			Files.copy(file.getInputStream(), filePath, REPLACE_EXISTING);
		} catch (Exception e) {
			throw new RuntimeException("Error to save a file : " + e.getMessage());
		}
	}

	@Override
	public void delete(String folder) {
		try {
			String imageDir = root + "/" + folder;
			Path uploadPath = Paths.get(imageDir);
			if (Files.exists(uploadPath)) {
				FileSystemUtils.deleteRecursively(uploadPath);
			}
		} catch (Exception e) {
			throw new RuntimeException("Error to delete file. " + e.getMessage());
		}
	}

	@Override
	public Resource read(String folder, String fileName) {
		try {
			String imageDir = root + "/" + folder;
			Path uploadPath = Paths.get(imageDir);

			Path file = uploadPath.resolve(fileName);
			Resource resource = new UrlResource(file.toUri());

			if (resource.exists() || resource.isReadable()) {
				return resource;
			}
			else {
				throw new RuntimeException("Errot to read the file");
			}
		} catch (MalformedURLException e) {
			throw new RuntimeException("Error: " + e.getMessage());
		}
	}
}
