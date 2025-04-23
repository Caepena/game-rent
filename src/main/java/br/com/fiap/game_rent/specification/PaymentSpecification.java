package br.com.fiap.game_rent.specification;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.jpa.domain.Specification;

import br.com.fiap.game_rent.controller.PaymentController.PaymentFilter;
import br.com.fiap.game_rent.model.Payments;
import jakarta.persistence.criteria.Predicate;

public class PaymentSpecification {
    
    public static Specification<Payments> withFilters(PaymentFilter filter) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (filter.description() != null && !filter.description().isBlank()) {
                predicates.add(
                    cb.like(
                        cb.lower(root.get("description")), "%" + filter.description().toLowerCase() + "%"));
            }

            if (filter.type() != null && !filter.type().isBlank()) {
                predicates.add(
                    cb.like(
                        cb.lower(root.get("type")), "%" + filter.type().toLowerCase() + "%"));
            }

            if (filter.game() != null && !filter.game().isBlank()) {
                predicates.add(
                    cb.like(
                        cb.lower(root.get("game").get("name")), "%" + filter.game().toLowerCase() + "%"));
            }

            if (filter.date() != null) {
                predicates.add(cb.equal(root.get("date"), filter.date()));
            }

            if (filter.amount() != null) {
                predicates.add(cb.equal(root.get("amount"), filter.amount()));
            }
            
            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}
