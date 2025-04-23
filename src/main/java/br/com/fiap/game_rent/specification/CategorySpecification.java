package br.com.fiap.game_rent.specification;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.jpa.domain.Specification;

import br.com.fiap.game_rent.controller.CategoryController.CategoryFilter;
import br.com.fiap.game_rent.model.Category;
import jakarta.persistence.criteria.Predicate;

public class CategorySpecification {
    
    public static Specification<Category> withFilters(CategoryFilter filter) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (filter.name() != null && !filter.name().isBlank()) {
                predicates.add(cb.like(cb.lower(root.get("name")), "%" + filter.name().toLowerCase() + "%"));
            }
            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}
