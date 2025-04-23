package br.com.fiap.game_rent.model;

import java.math.BigDecimal;
import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Payments {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Descrição é obrigatória")
    @Size(min = 5, max = 100, message = "Descrição deve ter entre 5 e 100 caracteres")
    private String description;

    @Positive(message = "Valor deve ser maior que zero")
    private BigDecimal amount;

    @NotNull(message = "Data de pagamento é obrigatória")
    @PastOrPresent(message = "A data não pode estar no futuro")
    private LocalDate date;    

    @NotNull(message = "Tipo de pagamento é obrigatório")
    @Enumerated(EnumType.STRING)
    private PaymentType type;

    @NotNull(message = "Jogo é obrigatório")
    @ManyToOne
    private Game game;

}
