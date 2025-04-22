package br.com.fiap.game_rent.controller;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
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
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/categories")
@Slf4j
public class CategoryController {

    @Autowired
    private CategoryRepository repository;

    @GetMapping
    @Cacheable("categories")
    @Operation(summary = "Listar todas as categorias", description = "Retorna uma lista com todas as categorias cadastradas no sistema", tags = "categories", responses = {
            @ApiResponse(responseCode = "200", description = "Lista de categorias retornada com sucesso")
    })
    public List<Category> index() {
        log.info("Buscando todas categorias");
        return repository.findAll();
    }

    @PostMapping
    @CacheEvict(value = "categories", allEntries = true)
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Cadastrar nova categoria", description = "Cria uma nova categoria com os dados informados", tags = "categories", responses = {
            @ApiResponse(responseCode = "201", description = "Categoria criada com sucesso"),
            @ApiResponse(responseCode = "400", description = "Falha na validação dos dados")
    })
    public Category create(@RequestBody @Valid Category category) {
        log.info("Cadastrando categoria: " + category.getName());
        return repository.save(category);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar categoria por ID", description = "Retorna os dados da categoria correspondente ao ID informado", tags = "categories", responses = {
            @ApiResponse(responseCode = "200", description = "Categoria encontrada"),
            @ApiResponse(responseCode = "404", description = "Categoria não encontrada")
    })
    public Category get(@PathVariable Long id) {
        log.info("Buscando categoria... \n" + id);
        return getCategory(id);
    }

    @DeleteMapping("/{id}")
    @CacheEvict(value = "categories", allEntries = true)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Excluir categoria", description = "Remove a categoria com o ID informado", tags = "categories", responses = {
            @ApiResponse(responseCode = "204", description = "Categoria removida com sucesso"),
            @ApiResponse(responseCode = "404", description = "Categoria não encontrada")
    })
    public void destroy(@PathVariable Long id) {
        log.info("Apagando categoria " + id);
        repository.delete(getCategory(id));
    }   

    @PutMapping("{id}")
    @CacheEvict(value = "categories", allEntries = true)
    @Operation(summary = "Atualizar categoria", description = "Atualiza os dados da categoria correspondente ao ID informado", tags = "categories", responses = {
            @ApiResponse(responseCode = "200", description = "Categoria atualizada com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos"),
            @ApiResponse(responseCode = "404", description = "Categoria não encontrada")
    })
    public Category update(@PathVariable Long id, @RequestBody @Valid Category category) {
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
