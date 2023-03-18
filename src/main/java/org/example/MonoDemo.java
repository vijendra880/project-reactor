package org.example;

import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

public class MonoDemo {
    public static void main(String[] args) {
        MonoDemo main = new MonoDemo();
        //main.testMono();
        //main.testMap();
        //main.testBlockingMono();
        //main.testNonBlockingMono();
        //main.fromCallableVsJust();
    }

    private void testMono() {
        Mono<Integer> publisher = Mono.just(someFunction()); //It will create mono of 1 item and someFunction will act as data source
        /*
        here subscriber will call subscribe method of publisher and
        it will pass subscriber implementation that will act on emitted data when publisher send data using onNext()
         */
        publisher.subscribe(i -> System.out.println("Consuming from Publisher's onNext " + i));
    }

    /*
    In case of Just someFunction method(data source) will be eagerly without subscribing while in from Callable it will not be executed until subscription is not done.
    In Just someFunction() will be executed only once no matter we subscribe any number of time while in case of callable someFunction will be executed every time subscription is done.
     */
    private void fromCallableVsJust() {
        Mono<Integer> callableMono = Mono.fromCallable(() -> someFunction());
        callableMono.subscribe(s -> System.out.println("Consuming1 from callable publisher " + s));
        callableMono.subscribe(s -> System.out.println("Consuming2 from callable publisher " + s));

        Mono<Integer> justMono = Mono.just(someFunction());
        justMono.subscribe(s -> System.out.println("Consuming1 from just publisher " + s));
        justMono.subscribe(s -> System.out.println("Consuming2 from just publisher " + s));
    }

    private Integer someFunction() {
        System.out.println("calling someFunction");
        return 1;
    }


    /*
    In this method third getName() will be blocked until second getName() is not done with consumption
    because here it's consuming in main thread only
     */
    private void testBlockingMono() {
        getName();
        getName()
            .subscribe(s -> System.out.println(s));
        getName();
    }

    /*
    Here third getName will not be blocked as second getName is consuming in other Thread.
     */
    private void testNonBlockingMono() {
        getName();
        getName()
            .subscribeOn(Schedulers.boundedElastic())
            .subscribe(s -> System.out.println(s));
        getName();
        sleepUtil(3000);
    }

    private void testMap() {
        Mono<Integer> publisher = Mono.just(1).map(i -> i * 2);
        publisher.subscribe(i -> System.out.println(i));
    }

    private Mono<String> getName() {
        System.out.println("Getting name");
        return Mono.fromSupplier(() -> {
            System.out.println("Started");
            sleepUtil(2000);
            return "vijendra";
        }).map(s -> s.toUpperCase());
    }



    private void sleepUtil(int milliSec) {
        try {
            Thread.sleep(milliSec);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

}
