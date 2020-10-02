package br.com.atlantico.controllers.reactive;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@CrossOrigin
public class FooReactiveController {

    @GetMapping("/reactive/foo")
    public Mono<String> foo() {
        return Mono.just("BAR");
    }

    @GetMapping(value = "/reactive/bar", produces = {MediaType.TEXT_EVENT_STREAM_VALUE})
    public Flux<String> bar() {
        return Flux.create(stringFluxSink -> {
            for (int i = 0; i < 10; i++) {
                stringFluxSink.next(String.valueOf(i + 1).concat("- FOO"));
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            stringFluxSink.complete();
        });
    }

}
