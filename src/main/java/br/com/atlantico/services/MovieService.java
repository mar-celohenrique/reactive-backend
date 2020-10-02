package br.com.atlantico.services;

import br.com.atlantico.clients.OMDBClient;
import br.com.atlantico.clients.RottenClient;
import br.com.atlantico.dtos.MovieDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.time.Duration;

@Slf4j
@Service
@RequiredArgsConstructor
public class MovieService {

    private final OMDBClient omdbClient;

    private final RottenClient rottenClient;

    public Flux<MovieDTO> search(String name) {
        Flux<MovieDTO> omdbMovies = this.omdbClient.search(name);

        Flux<MovieDTO> rottenMovies = this.rottenClient.search(name);

        return omdbMovies.delayElements(Duration.ofMillis(150)).concatWith(rottenMovies);
    }

}
