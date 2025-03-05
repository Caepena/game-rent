package br.com.fiap.game_rent.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.fiap.game_rent.model.Category;

@RestController
public class CategoryController {

    private List<Category> repository = new ArrayList<>();
    
    // GETALL
    @GetMapping(path = "/categories")
    public List<Category> index() {
        return repository;
    }

    // POST
    @PostMapping(path = "/categories")
    @ResponseStatus(code = HttpStatus.CREATED)
    public ResponseEntity<Category> create(@RequestBody Category category) {
        System.out.println("Cadastrando categoria: " + category.getName());
        repository.add(category);
        return ResponseEntity.status(201).body(category);
    }

    // GET
    @GetMapping(path = "/categories/{id}")
    public ResponseEntity<Category> get(@PathVariable Long id) {
        System.out.println("Buscando categoria... \n" + id);
        var category = repository.stream()
        .filter(c -> c.getId().equals(id))
        .findFirst();
        if (category.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(category.get());
    }

}
