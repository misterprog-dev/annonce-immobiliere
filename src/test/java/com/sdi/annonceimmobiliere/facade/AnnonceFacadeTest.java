package com.sdi.annonceimmobiliere.facade;

import static java.util.Arrays.asList;
import static java.util.Optional.empty;
import static java.util.Optional.ofNullable;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hibernate.validator.internal.util.CollectionHelper.asSet;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.sdi.annonceimmobiliere.domain.Annonce;
import com.sdi.annonceimmobiliere.presentation.factory.AnnonceVoFactory;
import com.sdi.annonceimmobiliere.presentation.vo.AnnonceVO;
import com.sdi.annonceimmobiliere.repository.AnnonceRepository;
import com.sdi.annonceimmobiliere.service.FileUploadService;

@ExtendWith(SpringExtension.class)
public class AnnonceFacadeTest {

	@Mock
	private AnnonceRepository annonceRepository;

	@Mock
	private AnnonceVoFactory annonceVoFactory;

	@Mock
	private FileUploadService fileUploadService;

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
				.containsExactly("Anonce 1", "Anonce 2");
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
	public void shouldReadAd_WhenReadAd_WithException() {
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
	}

	@Test
	public void shouldDeleteAd_WhenDeleteAd_WithException() {
		// GIVEN
		when(annonceRepository.findById(1L)).thenReturn(empty());

		// WHEN
		EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> {
			annonceFacade.readAd(1L);
		});

		// THEN
		verify(annonceRepository).findById(1L);
		verify(annonceRepository, never()).delete(annonce1);
		assertThat(exception.getMessage()).isEqualTo("Entity not found");
	}



}
