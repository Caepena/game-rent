package br.com.fiap.game_rent.specification;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.jpa.domain.Specification;

import br.com.fiap.game_rent.controller.GameController.GameFilter;
import br.com.fiap.game_rent.model.Game;
import jakarta.persistence.criteria.Predicate;

public class GameSpecification {
    
    public static Specification<Game> withFilters(GameFilter filter) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (filter.name() != null && !filter.name().isBlank()) {
                predicates.add(
                    cb.like(
                        cb.lower(root.get("name")), "%" + filter.name().toLowerCase() + "%"));
            }

            if (filter.category() != null && !filter.category().isBlank()) {
                predicates.add(
                    cb.like(
                        cb.lower(root.get("category").get("name")), "%" + filter.category().toLowerCase() + "%"));
            }

            if (filter.description() != null && !filter.description().isBlank()) {
                predicates.add(
                    cb.like(
                        cb.lower(root.get("description")), "%" + filter.description().toLowerCase() + "%"));
            }

            if (filter.price() != null) {
                predicates.add(cb.equal(root.get("price"), filter.price()));
            }
            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}
