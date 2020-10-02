package br.com.atlantico.clients;

import br.com.atlantico.configurations.OMDBClientConfiguration;
import br.com.atlantico.dtos.MovieDTO;
import br.com.atlantico.dtos.OMDBResponse;
import br.com.atlantico.mappers.OMDBMapper;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;

import java.time.Duration;

@Component
@RequiredArgsConstructor
public class OMDBClient {

    private final OMDBClientConfiguration omdbClientConfiguration;
    private final OMDBMapper omdbMapper;

    public Flux<MovieDTO> search(String name) {
        return this.omdbClientConfiguration.getOMDBClient().get()
                .uri("?s={name}&apikey=81f50d83", name)
                .retrieve()
                .bodyToFlux(String.class)
                .timeout(Duration.ofSeconds(this.omdbClientConfiguration.getTimeout()))
                .retry(this.omdbClientConfiguration.getMaxRetries())
                .flatMap(response -> {
                    ObjectMapper mapper = new ObjectMapper();
                    mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
                    try {
                        OMDBResponse omdbResponse = mapper.readValue(response, OMDBResponse.class);
                        return Flux.fromIterable(this.omdbMapper.mapFromEntityList(omdbResponse.getMovies()));
                    } catch (JsonProcessingException e) {
                        return Flux.empty();
                    }
                });

    }

}
