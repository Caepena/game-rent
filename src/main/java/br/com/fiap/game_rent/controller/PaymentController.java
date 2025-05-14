package br.com.fiap.game_rent.controller;

import java.math.BigDecimal;
import java.time.LocalDate;

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

import br.com.fiap.game_rent.model.Payments;
import br.com.fiap.game_rent.repository.PaymentsRepository;
import br.com.fiap.game_rent.specification.PaymentSpecification;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/payments")
@Slf4j
public class PaymentController {

    public record PaymentFilter(String description, String type, String game, LocalDate date, BigDecimal amount) {
    }

    @Autowired
    private PaymentsRepository repository;

    @GetMapping
    @Cacheable("payments")
    @Operation(description = "Lista todos os pagamentos", tags = "payments", summary = "List all payments")
    public Page<Payments> index(PaymentFilter filter,
            @PageableDefault(size = 10, sort = "id", direction = Direction.DESC) Pageable pageable) {
        var specification = PaymentSpecification.withFilters(filter);
        return repository.findAll(specification, pageable);

    }

    @PostMapping
    @CacheEvict(value = "payments", allEntries = true)
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(responses = {
            @ApiResponse(responseCode = "400", description = "Invalid payment data"),
            @ApiResponse(responseCode = "201", description = "Payment created successfully")
    })
    public Payments create(@RequestBody @Valid Payments payment) {
        log.info("Criando pagamento: " + payment.getId());
        return repository.save(payment);
    }

    @GetMapping("{id}")
    @Operation(description = "Busca um pagamento pelo ID", tags = "payments", summary = "Get payment by ID", responses = {
            @ApiResponse(responseCode = "200", description = "Payment found"),
            @ApiResponse(responseCode = "404", description = "Payment not found")
    })
    public Payments get(Long id) {
        log.info("Buscando pagamento: " + id);
        return getPayment(id);
    }

    @DeleteMapping("{id}")
    @CacheEvict(value = "payments", allEntries = true)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(description = "Deleta um pagamento pelo ID", tags = "payments", summary = "Delete payment by ID", responses = {
            @ApiResponse(responseCode = "204", description = "Payment deleted"),
            @ApiResponse(responseCode = "404", description = "Payment not found")
    })
    public void delete(@PathVariable Long id) {
        log.info("Deletando pagamento: " + id);
        repository.delete(getPayment(id));
    }

    @PutMapping("{id}")
    @CacheEvict(value = "payments", allEntries = true)
    @Operation(description = "Atualiza um pagamento pelo ID", tags = "payments", summary = "Update payment by ID", responses = {
            @ApiResponse(responseCode = "200", description = "Payment updated"),
            @ApiResponse(responseCode = "404", description = "Payment not found")
    })
    public Payments update(@PathVariable Long id, Payments payment) {
        log.info("Atualizando pagamento: " + id);

        getPayment(id);
        payment.setId(id);
        return repository.save(payment);
    }

    private Payments getPayment(Long id) {
        return repository.findById(id).orElseThrow(() -> new RuntimeException("Pagamento não encontrado"));
    }
}
