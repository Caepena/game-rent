package br.com.fiap.game_rent.config;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.fiap.game_rent.model.Category;
import br.com.fiap.game_rent.repository.CategoryRepository;
import jakarta.annotation.PostConstruct;

@Component
public class DatabaseSeeder {

    @Autowired
    private CategoryRepository categoryRepository;

    @PostConstruct
    public void categorySeed() {
        categoryRepository.saveAll(
                List.of(
                        Category.builder().name("Terror").build(),
                        Category.builder().name("Corrida").build(),
                        Category.builder().name("Rogue Like").build()));
    }
}
