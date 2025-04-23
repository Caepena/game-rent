package br.com.fiap.game_rent.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import br.com.fiap.game_rent.model.Payments;

public interface PaymentsRepository extends JpaRepository<Payments, Long>, JpaSpecificationExecutor<Payments> {

    
}
