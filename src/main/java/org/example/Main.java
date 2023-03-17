package org.example;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@Slf4j
public class Main {
    public static void main(String[] args) {
        Main main = new Main();
        main.testMono();
    }

    private void testMono() {
        Mono<Integer> publisher = Mono.just(1); //It will create mono of 1 item
        publisher.subscribe(i -> System.out.println(i)); // here subscriber will call subscribe method of publisher and it will pass consumer
    }


}