package pe.edu.vallegrande.shed.service;

import pe.edu.vallegrande.shed.dto.SupplierDTO;
import pe.edu.vallegrande.shed.dto.HenDTO;
import pe.edu.vallegrande.shed.model.Shed;
import pe.edu.vallegrande.shed.repository.ShedRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class ShedService {

    // WebClient para supplier
    private final WebClient supplierWebClient = WebClient.builder()
        .baseUrl("https://ms-supplier.onrender.com/NPH/suppliers")
        .defaultHeader("Content-Type", "application/json")
        .build();

    // WebClient para hen
    private final WebClient henWebClient = WebClient.builder()
        .baseUrl("https://vgms-hen.onrender.com/hen")
        .defaultHeader("Content-Type", "application/json")
        .build();

    // Método para obtener supplier
    public Mono<SupplierDTO> getSupplierFromExternal(Long supplierId) {
        return supplierWebClient.get()
            .uri("/{id}", supplierId)
            .accept(MediaType.APPLICATION_JSON)
            .retrieve()
            .bodyToMono(SupplierDTO.class);
    }

    // Método para obtener hen
    public Mono<HenDTO> getHenFromExternal(Long henId) {
        return henWebClient.get()
            .uri("/{id}", henId)
            .accept(MediaType.APPLICATION_JSON)
            .retrieve()
            .bodyToMono(HenDTO.class);
    }

    @Autowired
    private ShedRepository shedRepository;

    public Mono<Shed> createShed(Shed shed) {
        return shedRepository.save(shed);
    }

    public Mono<Shed> updateShed(Long id, Shed shed) {
        return shedRepository.findById(id)
                .flatMap(existingShed -> {
                    existingShed.setName(shed.getName());
                    existingShed.setLocation(shed.getLocation());
                    existingShed.setCapacity(shed.getCapacity());
                    existingShed.setChickenType(shed.getChickenType());
                    existingShed.setInspectionDate(shed.getInspectionDate());
                    existingShed.setNote(shed.getNote());
                    return shedRepository.save(existingShed);
                });
    }

    public Flux<Shed> getAllSheds() {
        return shedRepository.findAll();
    }

    public Mono<Shed> deleteShed(Long id) {
        return shedRepository.findById(id)
                .flatMap(existingShed -> {
                    existingShed.setStatus("I");
                    return shedRepository.save(existingShed);
                });
    }

    public Mono<Shed> restoreShed(Long id) {
        return shedRepository.findById(id)
                .flatMap(existingShed -> {
                    existingShed.setStatus("A");
                    return shedRepository.save(existingShed);
                });
    }

    public Mono<Void> permanentDeleteShed(Long id) {
        return shedRepository.deleteById(id);
    }
}
