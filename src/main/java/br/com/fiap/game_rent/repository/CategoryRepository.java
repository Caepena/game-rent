package br.com.fiap.game_rent.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.fiap.game_rent.model.Category;

//EQUIVALENTE AO DAO
public interface CategoryRepository extends JpaRepository<Category, Long>{
    
}
