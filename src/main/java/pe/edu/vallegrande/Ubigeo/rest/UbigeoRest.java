package pe.edu.vallegrande.Ubigeo.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import pe.edu.vallegrande.Ubigeo.model.Ubigeo;
import pe.edu.vallegrande.Ubigeo.service.UbigeoService;
import reactor.core.publisher.Mono;
import reactor.core.publisher.Flux;

@RestController
@RequestMapping("/NPH/ubigeo")
public class UbigeoRest {

    @Autowired
    private UbigeoService ubigeoService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<Ubigeo> crear(@RequestBody Ubigeo ubigeo) {
        return ubigeoService.crear(ubigeo);
    }

    @GetMapping
    public Flux<Ubigeo> listarTodos() {
        return ubigeoService.listarTodos();
    }

    @PutMapping("/{id}")
    public Mono<Ubigeo> editar(@PathVariable Long id, @RequestBody Ubigeo ubigeo) {
        return ubigeoService.editar(id, ubigeo);
    }

    @DeleteMapping("/fisico/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Mono<Void> eliminarFisicamente(@PathVariable Long id) {
        return ubigeoService.eliminarFisicamente(id);
    }

    @PutMapping("/logico/{id}")
    public Mono<Ubigeo> eliminarLogico(@PathVariable Long id) {
        return ubigeoService.eliminarLogico(id);
    }

    @PutMapping("/restaurar/{id}")
    public Mono<Ubigeo> restaurar(@PathVariable Long id) {
        return ubigeoService.restaurar(id);
    }

}

