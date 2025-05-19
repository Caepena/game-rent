package br.com.fiap.game_rent.model;

import java.math.BigDecimal;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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
public class Game {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Campo obrigatório")
    private String name;

    @ManyToOne
    @NotNull(message = "Campo obrigatório")
    private Category category;

    @NotBlank(message = "Campo obrigatório")
    @Size(max = 200, message = "Máximo de 200 caracteres")
    private String description;

    @Positive(message = "Valor deve ser positivo")
    private BigDecimal price;
}
