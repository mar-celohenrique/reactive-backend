package br.com.atlantico.clients;

import br.com.atlantico.configurations.RottenClientConfiguration;
import br.com.atlantico.dtos.MovieDTO;
import br.com.atlantico.dtos.RottenResponse;
import br.com.atlantico.mappers.RottenMovieMapper;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;

import java.time.Duration;

@Component
@RequiredArgsConstructor
public class RottenClient {

    private final RottenClientConfiguration rottenClientConfiguration;
    private final RottenMovieMapper movieMapper;

    public Flux<MovieDTO> search(String name) {
        return this.rottenClientConfiguration.getRottenClient().get()
                .uri("?q={name}", name)
                .retrieve()
                .bodyToFlux(String.class)
                .timeout(Duration.ofSeconds(this.rottenClientConfiguration.getTimeout()))
                .retry(this.rottenClientConfiguration.getMaxRetries())
                .flatMap(response -> {
                    ObjectMapper mapper = new ObjectMapper();
                    mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
                    try {
                        RottenResponse rottenResponse = mapper.readValue(response, RottenResponse.class);
                        return Flux.fromIterable(this.movieMapper.mapFromEntityList(rottenResponse.getMovies()));
                    } catch (JsonProcessingException e) {
                        return Flux.empty();
                    }
                });
    }

}
