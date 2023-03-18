package org.example;

import reactor.core.publisher.Flux;

public class MapVsFlatMap {
    public static void main(String args[]) {
        MapVsFlatMap obj = new MapVsFlatMap();
        //obj.testMap();
        //obj.testFlatMap();
    }

    private void testMap() {
        Flux<Integer> publisher = Flux.just(1, 2).map(i -> i * 2);
        publisher.subscribe(i -> System.out.println(i));
    }

    private void testFlatMap() {
        Flux<Integer> userIds = getUserIds();
        Flux<Integer> orderIds = userIds.flatMap(userId -> getOrder(userId));
        //if we use map here as below then it will return Flux<Flux<Integer>> as map doesn't flatten the result
        //Flux<Flux<Integer>> orderIds1 = userIds.map(userId -> getOrder(userId));
        //so if in right side(getOrder(userId)) we are receiving Flux, then we should use flatmap to convert these multiple flux into sinle one,
        //otherwise if in right side(i*2) we are reviving plain value, then we should use map.
        orderIds.collectList().block().forEach(i -> System.out.println(i));
    }

    private Flux<Integer> getOrder(Integer userId) {
        return Flux.just(userId * 10);
    }

    private Flux<Integer> getUserIds() {
        return Flux.just(1, 2, 3);
    }
}
