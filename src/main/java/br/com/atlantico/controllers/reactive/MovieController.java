package br.com.atlantico.controllers.reactive;

import br.com.atlantico.dtos.MovieDTO;
import br.com.atlantico.dtos.OMDBMovieDTO;
import br.com.atlantico.services.MovieService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
@CrossOrigin
@RequiredArgsConstructor
public class MovieController {

    private final MovieService movieService;

    @GetMapping(value = "/movies/{name}", produces = {MediaType.TEXT_EVENT_STREAM_VALUE})
    public Flux<MovieDTO> search(@PathVariable("name") String name) {
        return this.movieService.search(name);
    }

}
