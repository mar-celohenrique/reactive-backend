package br.com.atlantico.controllers.mvc;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FooController {

    @GetMapping("/foo")
    public String foo() {
        return "BAR";
    }

    @GetMapping("/bar")
    public String[] bar() throws InterruptedException {
        String[] values = new String[10];

        for (int i = 0; i < 10; i++) {
            values[i] = String.valueOf(i + 1).concat("- FOO");
            Thread.sleep(500);
        }

        return values;
    }

}
