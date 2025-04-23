package br.com.fiap.game_rent.controller;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
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

import br.com.fiap.game_rent.model.Game;
import br.com.fiap.game_rent.repository.GameRepository;
import br.com.fiap.game_rent.specification.GameSpecification;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("games")
@Slf4j
public class GameController {

    public record GameFilter(String name, String category, String description, BigDecimal price) {
    }

    @Autowired
    private GameRepository repository;

    @GetMapping
    @Cacheable("games")
    @Operation(description = "Lista todos os games", tags = "games", summary = "List all games")
    public Page<Game> index(GameFilter filter,
            @PageableDefault(size = 10, sort = "category", direction = Direction.DESC) Pageable pageable) {
        var specification = GameSpecification.withFilters(filter);
        return repository.findAll(specification, pageable);

    }

    @PostMapping
    @CacheEvict(value = "games", allEntries = true)
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(responses = {
            @ApiResponse(responseCode = "400", description = "Invalid game data"),
            @ApiResponse(responseCode = "201", description = "Game created successfully")
    })
    public Game create(@RequestBody @Valid Game game) {
        log.info("Criando game: " + game.getName());
        return repository.save(game);
    }

    @Operation(summary = "Busca um game pelo ID", description = "Retorna um game com o ID informado", tags = "games", responses = {
            @ApiResponse(responseCode = "200", description = "Game encontrado"),
            @ApiResponse(responseCode = "404", description = "Game não encontrado")
    })
    @GetMapping("{id}")
    public Game get(Long id) {
        log.info("Buscando game: " + id);
        return getGame(id);
    }

    @Operation(summary = "Deleta um game pelo ID", description = "Remove um game com o ID informado", tags = "games", responses = {
            @ApiResponse(responseCode = "204", description = "Game removido com sucesso"),
            @ApiResponse(responseCode = "404", description = "Game não encontrado")
    })
    @DeleteMapping("{id}")
    @CacheEvict(value = "games", allEntries = true)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        log.info("Deletando game: " + id);
        repository.delete(getGame(id));
    }

    @Operation(summary = "Atualiza um game pelo ID", description = "Atualiza um game com o ID informado", tags = "games", responses = {
            @ApiResponse(responseCode = "200", description = "Game atualizado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Game não encontrado")
    })
    @PutMapping("{id}")
    @CacheEvict(value = "games", allEntries = true)
    public Game update(@PathVariable Long id, @RequestBody @Valid Game game) {
        log.info("Atualizando game: " + id);
        
        getGame(id);
        game.setId(id);
        return repository.save(game);
    }

    private Game getGame(Long id) {
        return repository.findById(id).orElseThrow(() -> new RuntimeException("Game not found"));
    }
}
