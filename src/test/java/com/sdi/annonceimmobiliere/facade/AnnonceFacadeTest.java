package com.sdi.annonceimmobiliere.facade;

import static java.util.Arrays.asList;
import static java.util.Optional.empty;
import static java.util.Optional.of;
import static java.util.Optional.ofNullable;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hibernate.validator.internal.util.CollectionHelper.asSet;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.ALL;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.multipart.MultipartFile;

import com.sdi.annonceimmobiliere.domain.Annonce;
import com.sdi.annonceimmobiliere.presentation.factory.AnnonceVoFactory;
import com.sdi.annonceimmobiliere.presentation.vo.AnnonceVO;
import com.sdi.annonceimmobiliere.repository.AnnonceRepository;
import com.sdi.annonceimmobiliere.service.FileStorageService;

@ExtendWith(SpringExtension.class)
public class AnnonceFacadeTest {

	@Mock
	private AnnonceRepository annonceRepository;

	@Mock
	private AnnonceVoFactory annonceVoFactory;

	@Mock
	private FileStorageService fileStorageService;

	@InjectMocks
	@Spy
	private AnnonceFacade annonceFacade;

	private Annonce annonce1, annonce2;
	private AnnonceVO annonce1VO, annonce2VO;

	@BeforeEach
	public void setUp() {
		annonce1 = new Annonce("Anonce 1", "Desc 1", "fil1");
		annonce2 = new Annonce("Anonce 2", "Desc 2", "fil2");
		annonce1VO = new AnnonceVO(annonce1);
		annonce2VO = new AnnonceVO(annonce2);
	}

	@Test
	public void shouldReadAds_WhenReadAds() {
		// GIVEN
		List<Annonce> ads = asList(annonce1, annonce2);
		when(annonceRepository.findAll()).thenReturn(ads);
		when(annonceVoFactory.annoncesVO(ads)).thenReturn(asSet(annonce1VO, annonce2VO));

		// WHEN
		Set<AnnonceVO> results = annonceFacade.readAds();

		// THEN
		verify(annonceRepository).findAll();
		verify(annonceVoFactory).annoncesVO(ads);
		assertThat(results)
				.hasSize(2)
				.extracting(AnnonceVO::getTitle)
				.containsOnly("Anonce 1", "Anonce 2");
	}

	@Test
	public void shouldReadAd_WhenReadAd() {
		// GIVEN
		when(annonceRepository.findById(1L)).thenReturn(ofNullable(annonce1));
		when(annonceVoFactory.annonceVO(annonce1)).thenReturn(annonce1VO);

		// WHEN
		AnnonceVO result = annonceFacade.readAd(1L);

		// THEN
		verify(annonceRepository).findById(1L);
		verify(annonceVoFactory).annonceVO(annonce1);
		assertThat(result)
				.extracting(AnnonceVO::getTitle)
				.isEqualTo("Anonce 1");
	}

	@Test
	public void shouldLeverException_WhenReadAd_WithException() {
		// GIVEN
		when(annonceRepository.findById(1L)).thenReturn(empty());

		// WHEN
		EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> {
			annonceFacade.readAd(1L);
		});

		// THEN
		verify(annonceRepository).findById(1L);
		verify(annonceVoFactory, never()).annonceVO(annonce1);
		assertThat(exception.getMessage()).isEqualTo("Entity not found");
	}

	@Test
	public void shouldDeleteAd_WhenDeleteAd() {
		// GIVEN
		when(annonceRepository.findById(1L)).thenReturn(ofNullable(annonce1));

		// WHEN
		annonceFacade.deleteAd(1L);

		// THEN
		verify(annonceRepository).findById(1L);
		verify(annonceRepository).delete(annonce1);
		verify(fileStorageService).delete("1");
	}

	@Test
	public void shouldLeverException_WhenDeleteAd_WithException() {
		// GIVEN
		when(annonceRepository.findById(1L)).thenReturn(empty());

		// WHEN
		EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> {
			annonceFacade.readAd(1L);
		});

		// THEN
		verify(annonceRepository).findById(1L);
		verify(annonceRepository, never()).delete(annonce1);
		verify(fileStorageService, never()).delete("1");
		assertThat(exception.getMessage()).isEqualTo("Entity not found");
	}

	@Test
	public void shouldSave_WhenSave_WithImage(){
		// GIVEN
		AnnonceVO annonceVO = mock(AnnonceVO.class);
		when(annonceVO.getTitle()).thenReturn("A");
		when(annonceVO.getDescription()).thenReturn("AB");
		Annonce annonce = mock(Annonce.class);
		when(annonce.getId()).thenReturn(1L);
		MultipartFile file = new MockMultipartFile("Image.png", "Image.png", ALL.getType(), new byte[123]);
		when(annonceRepository.save(any())).thenReturn(annonce);

		// WHEN
		annonceFacade.save(annonceVO, file);

		// THEN
		verify(annonceRepository).save(any());
		verify(fileStorageService).save(file, "1", false);
	}

	@Test
	public void shouldSave_WhenSave_WithoutImage(){
		// GIVEN
		AnnonceVO annonceVO = mock(AnnonceVO.class);
		when(annonceVO.getTitle()).thenReturn("A");
		when(annonceVO.getDescription()).thenReturn("AB");
		Annonce annonce = mock(Annonce.class);
		when(annonce.getId()).thenReturn(1L);
		when(annonceRepository.save(any())).thenReturn(annonce);

		// WHEN
		annonceFacade.save(annonceVO, null);

		// THEN
		verify(annonceRepository).save(any());
		verify(fileStorageService, never()).save(null, "1", false);
	}

	@Test
	public void shouldUpdate_WhenUpdate_WithImage() {
		// GIVEN
		AnnonceVO annonceVO = mock(AnnonceVO.class);
		when(annonceVO.getTitle()).thenReturn("A");
		when(annonceVO.getDescription()).thenReturn("AB");

		Annonce annonce = mock(Annonce.class);
		when(annonce.getId()).thenReturn(1L);
		when(annonceRepository.findById(1L)).thenReturn(of(annonce));

		MultipartFile file = new MockMultipartFile("Image.png", "Image.png", ALL.getType(), new byte[123]);

		// WHEN
		annonceFacade.update(1L, annonceVO, file);

		// THEN
		verify(annonceRepository).save(any());
		verify(fileStorageService).save(file, "1", true);
		verify(fileStorageService, never()).delete(any());
	}

	@Test
	public void shouldUpdate_WhenUpdate_WithoutImage() {
		// GIVEN
		AnnonceVO annonceVO = mock(AnnonceVO.class);
		when(annonceVO.getTitle()).thenReturn("A");
		when(annonceVO.getDescription()).thenReturn("AB");

		Annonce annonce = mock(Annonce.class);
		when(annonce.getId()).thenReturn(1L);
		when(annonceRepository.findById(1L)).thenReturn(of(annonce));


		// WHEN
		annonceFacade.update(1L, annonceVO, null);

		// THEN
		verify(annonceRepository).save(any());
		verify(fileStorageService, never()).save(null, "1", true);
		verify(fileStorageService).delete("1");
	}

}
