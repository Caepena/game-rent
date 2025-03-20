package br.com.fiap.game_rent.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import br.com.fiap.game_rent.model.Category;
import br.com.fiap.game_rent.repository.CategoryRepository;

@RestController
@RequestMapping("/categories")
public class CategoryController {

    private final Logger log = LoggerFactory.getLogger(getClass());

    @Autowired // injeção de dependência
    private CategoryRepository repository;

    // GETALL
    @GetMapping
    public List<Category> index() {
        log.info("Buscando todas categorias");
        return repository.findAll();
    }

    // POST
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Category create(@RequestBody Category category) {
        log.info("Cadastrando categoria: " + category.getName());
        return repository.save(category);
    }

    // GET
    @GetMapping("/{id}")
    public Category get(@PathVariable Long id) {
        log.info("Buscando categoria... \n" + id);
        return getCategory(id);
    }

    // apagar categoria @delete
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void destroy(@PathVariable Long id) {
        log.info("Apagando categoria " + id);
        repository.delete(getCategory(id));
    }

    // editar categoria @put
    @PutMapping("{id}")
    public Category update(@PathVariable Long id, @RequestBody Category category) {
        log.info("Atualizando categoria " + id + " " + category);

        getCategory(id);
        category.setId(id);
        return repository.save(category);
    }

    private Category getCategory(Long id) {
        return repository.findById(id)
                .orElseThrow(
                        () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Categoria não encontrada"));
    }

}
