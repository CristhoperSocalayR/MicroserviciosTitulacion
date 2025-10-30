package edu.pe.vallegrande.TypeKardex;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import edu.pe.vallegrande.TypeKardex.model.TypeKardex;
import edu.pe.vallegrande.TypeKardex.repository.TypeKardexRepository;
import edu.pe.vallegrande.TypeKardex.service.TypeKardexService;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.Mockito.*;

class TypeKardexApplicationTests {

	private TypeKardexService service;
	private TypeKardexRepository repository;

	@BeforeEach
	void setUp() {
		repository = mock(TypeKardexRepository.class);
		service = new TypeKardexService(repository);
	}

	@Test
	void testListAll() {
		TypeKardex k1 = new TypeKardex();
		k1.setId(1L);
		TypeKardex k2 = new TypeKardex();
		k2.setId(2L);
		TypeKardex k3 = new TypeKardex();
		k2.setId(3L);

		when(repository.findAllByOrderByIdAsc()).thenReturn(Flux.just(k1, k2, k3));

		StepVerifier.create(service.listAll())
				.expectNext(k1)
				.expectNext(k2)
				.expectNext(k3)
				.verifyComplete();
	}

	@Test
	void testDeleteTypeKardexById() {
		Long idToDelete = 5L;
		when(repository.deleteById(idToDelete)).thenReturn(Mono.empty());

		StepVerifier.create(service.deletePhysical(idToDelete))
				.verifyComplete();

		verify(repository, times(1)).deleteById(idToDelete);
	}

	@Test
	void testCreateTypeKardex() {
		TypeKardex newKardex = new TypeKardex();
		newKardex.setId(10L);
		newKardex.setMaximumAmount(100);
		newKardex.setMinimumQuantity(10);
		newKardex.setSupplierId(1);
		newKardex.setProductId(1L);
		newKardex.setShedId(1L);
		newKardex.setDescription("Test Description");
		newKardex.setName("Test Kardex");
		newKardex.setStatus("A");

		when(repository.save(any(TypeKardex.class))).thenReturn(Mono.just(newKardex));

		StepVerifier.create(service.create(newKardex))
				.expectNextMatches(saved -> saved.getId() == 10L && "Test Kardex".equals(saved.getName()))
				.verifyComplete();

		verify(repository, times(1)).save(newKardex);
	}
     
}
