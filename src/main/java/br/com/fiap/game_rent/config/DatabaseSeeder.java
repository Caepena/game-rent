package br.com.fiap.game_rent.config;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.fiap.game_rent.model.Category;
import br.com.fiap.game_rent.model.Game;
import br.com.fiap.game_rent.model.PaymentType;
import br.com.fiap.game_rent.model.Payments;
import br.com.fiap.game_rent.repository.CategoryRepository;
import br.com.fiap.game_rent.repository.GameRepository;
import br.com.fiap.game_rent.repository.PaymentsRepository;
import jakarta.annotation.PostConstruct;

@Component
public class DatabaseSeeder {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private GameRepository gameRepository;

    @Autowired
    private PaymentsRepository paymentsRepository;

    @PostConstruct
    public void init() {
        var categories = List.of(
                Category.builder().name("Terror").build(),
                Category.builder().name("Corrida").build(),
                Category.builder().name("Rogue Like").build());

        categoryRepository.saveAll(categories);

        var games = List.of(
                // Jogos de Terror
                Game.builder()
                        .name("Resident Evil")
                        .description(
                                "Após um vazamento de um vírus feito em laboratório para ser usado como uma arma biológica, soldados da S.T.A.R.S entram em uma mansão cheia de zumbis e monstros.")
                        .price(new BigDecimal("199.90"))
                        .category(categories.get(0)).build(),
                Game.builder()
                        .name("Silent Hill 2")
                        .description(
                                "James Sunderland chega à cidade de Silent Hill após receber uma carta da sua esposa falecida, mergulhando em um terror psicológico profundo.")
                        .price(new BigDecimal("179.90"))
                        .category(categories.get(0)).build(),
                Game.builder()
                        .name("Outlast")
                        .description(
                                "Um jornalista investiga um asilo psiquiátrico abandonado e se depara com experiências bizarras e aterrorizantes.")
                        .price(new BigDecimal("89.90"))
                        .category(categories.get(0)).build(),

                // Jogos de Corrida
                Game.builder()
                        .name("Need for Speed: Most Wanted")
                        .description(
                                "Corra pelas ruas da cidade enquanto foge da polícia e desafia os corredores da Blacklist.")
                        .price(new BigDecimal("129.90"))
                        .category(categories.get(1)).build(),
                Game.builder()
                        .name("Gran Turismo 7")
                        .description(
                                "Simulador de corrida realista com uma vasta coleção de carros e pistas pelo mundo.")
                        .price(new BigDecimal("249.90"))
                        .category(categories.get(1)).build(),
                Game.builder()
                        .name("Mario Kart 8 Deluxe")
                        .description(
                                "Corrida divertida com os personagens da Nintendo, usando itens e atalhos malucos.")
                        .price(new BigDecimal("299.90"))
                        .category(categories.get(1)).build(),

                // Jogos Rogue Like
                Game.builder()
                        .name("Hades")
                        .description(
                                "Jogue como Zagreus, filho de Hades, tentando escapar do submundo em um combate ágil e desafiador.")
                        .price(new BigDecimal("99.90"))
                        .category(categories.get(2)).build(),
                Game.builder()
                        .name("Dead Cells")
                        .description(
                                "Explore um castelo em constante mutação, derrotando inimigos e coletando melhorias em runs únicas.")
                        .price(new BigDecimal("59.90"))
                        .category(categories.get(2)).build(),
                Game.builder()
                        .name("The Binding of Isaac")
                        .description(
                                "Ajude Isaac a escapar de sua mãe fanática religiosa em um calabouço sombrio cheio de monstros bizarros.")
                        .price(new BigDecimal("69.90"))
                        .category(categories.get(2)).build());

        gameRepository.saveAll(games);

        var allGames = gameRepository.findAll();
        var types = PaymentType.values();
        var payments = new ArrayList<Payments>();
        for (int i = 0; i < 50; i++) {
            Game game = allGames.get(i % allGames.size());
            PaymentType type = types[i % types.length];

            Payments payment = Payments.builder()
                    .price(game.getPrice())
                    .date(LocalDate.now().minusDays(i % 30))
                    .type(type)
                    .game(game)
                    .build();

            payments.add(payment);
        }
        paymentsRepository.saveAll(payments);
    }
}
