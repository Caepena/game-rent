package br.com.fiap.game_rent.controller;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import br.com.fiap.game_rent.model.Category;

@RestController
public class CategoryController {
    
    private final Logger log = LoggerFactory.getLogger(getClass());

    private List<Category> repository = new ArrayList<>();

    // GETALL
    @GetMapping(path = "/categories")
    public List<Category> index() {
        log.info("Buscando todas categorias");
        return repository;
    }

    // POST
    @PostMapping(path = "/categories")
    @ResponseStatus(code = HttpStatus.CREATED)
    public ResponseEntity<Category> create(@RequestBody Category category) {
        log.info("Cadastrando categoria: " + category.getName());
        repository.add(category);
        return ResponseEntity.status(201).body(category);
    }

    // GET
    @GetMapping(path = "/categories/{id}")
    public ResponseEntity<Category> get(@PathVariable Long id) {
        log.info("Buscando categoria... \n" + id);
        return ResponseEntity.ok(getCategory(id));
    }

    // apagar categoria @delete
    @DeleteMapping("/categories/{id}")
    public ResponseEntity<Object> destroy(@PathVariable Long id) {
        log.info("Apagando categoria " + id);
        repository.remove(getCategory(id));
        return ResponseEntity.noContent().build();

    }

    // editar categoria @put
    @PutMapping("/categories/{id}")
    public ResponseEntity<Category> update(@PathVariable Long id,@RequestBody Category category){
        log.info("Atualizando categoria " + id + " " + category);

        var categoryToUpdate = getCategory(id);

        repository.remove(categoryToUpdate);
        category.setId(id);
        repository.add(category);
        return ResponseEntity.ok(category);
    }

    private Category getCategory(Long id) {
        return repository.stream()
        
        .filter(c -> c.getId().equals(id))
        .findFirst()
        .orElseThrow(
            () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Categoria não encontrada")
        );
    }

}
