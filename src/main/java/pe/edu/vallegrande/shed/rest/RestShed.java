package pe.edu.vallegrande.shed.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import pe.edu.vallegrande.shed.model.Shed;
import pe.edu.vallegrande.shed.service.ShedService;
import reactor.core.publisher.Mono;
import reactor.core.publisher.Flux;

@RestController
@RequestMapping("/NPH/sheds")
public class RestShed {

    @Autowired
    private ShedService shedService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<Shed> createShed(@RequestBody Shed shed) {
        return shedService.createShed(shed);
    }

    @PutMapping("/{id}")
    public Mono<Shed> updateShed(@PathVariable Long id, @RequestBody Shed shed) {
        return shedService.updateShed(id, shed);
    }

    @GetMapping
    public Flux<Shed> getAllSheds() {
        return shedService.getAllSheds();
    }

    @DeleteMapping("/logic/{id}")
    public Mono<Shed> deleteShed(@PathVariable Long id) {
        return shedService.deleteShed(id);
    }

    @PutMapping("/restore/{id}")
    public Mono<Shed> restoreShed(@PathVariable Long id) {
        return shedService.restoreShed(id);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Mono<Void> permanentDeleteShed(@PathVariable Long id) {
        return shedService.permanentDeleteShed(id);
    }
}