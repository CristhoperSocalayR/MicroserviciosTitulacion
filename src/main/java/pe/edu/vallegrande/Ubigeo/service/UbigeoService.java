package pe.edu.vallegrande.Ubigeo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pe.edu.vallegrande.Ubigeo.model.Ubigeo;
import pe.edu.vallegrande.Ubigeo.repository.UbigeoRepository;
import reactor.core.publisher.Mono;
import reactor.core.publisher.Flux;


@Service
public class UbigeoService {

    @Autowired
    private UbigeoRepository ubigeoRepository;

    public Flux<Ubigeo> listarTodos() {
        return ubigeoRepository.findAllByOrderByIdAsc();
    }

    @Transactional
    public Mono<Ubigeo> editar(Long id, Ubigeo ubigeo) {
        return ubigeoRepository.findById(id)
                .flatMap(existingUbigeo -> {
                    existingUbigeo.setCountry(ubigeo.getCountry());
                    existingUbigeo.setDepartment(ubigeo.getDepartment());
                    existingUbigeo.setProvince(ubigeo.getProvince());
                    existingUbigeo.setDistrict(ubigeo.getDistrict());
                    existingUbigeo.setStatus(ubigeo.getStatus());
                    return ubigeoRepository.save(existingUbigeo);
                });
    }

    @Transactional
    public Mono<Void> eliminarFisicamente(Long id) {
        return ubigeoRepository.deleteById(id);
    }

    @Transactional
    public Mono<Ubigeo> eliminarLogico(Long id) {
        return ubigeoRepository.findById(id)
                .flatMap(existingUbigeo -> {
                    existingUbigeo.setStatus("I");
                    return ubigeoRepository.save(existingUbigeo);
                });
    }

    @Transactional
    public Mono<Ubigeo> restaurar(Long id) {
        return ubigeoRepository.findById(id)
                .flatMap(existingUbigeo -> {
                    existingUbigeo.setStatus("A");
                    return ubigeoRepository.save(existingUbigeo);
                });
    }

    @Transactional
    public Mono<Ubigeo> crear(Ubigeo ubigeo) {
        return ubigeoRepository.save(ubigeo);
    }
}

