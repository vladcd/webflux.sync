package com.vladcarcu.webflux.sync.util;

import com.vladcarcu.webflux.sync.entity.Student;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.Callable;

public class AsyncUtil {

    private AsyncUtil() {
    }

    public static <T> Mono<T> wrapMonoOptional(Callable<Optional<T>> supplier) {
        var blockingWrapper = Mono.fromCallable(supplier)
                .flatMap(optional -> optional.map(Mono::just).orElseGet(Mono::empty));
        return blockingWrapper.subscribeOn(Schedulers.boundedElastic());
    }

    public static <T> Mono<T> wrapMono(Callable<T> supplier) {
        var blockingWrapper = Mono.fromCallable(supplier);
        return blockingWrapper.subscribeOn(Schedulers.boundedElastic());
    }

    public static Mono<Void> wrapMonoVoid(Runnable supplier) {
        var blockingWrapper = Mono.fromRunnable(supplier).then();
        return blockingWrapper.subscribeOn(Schedulers.boundedElastic());
    }

    public static <T> Flux<T> wrapFlux(Callable<List<T>> supplier) {
        var blockingWrapper = Mono.fromCallable(supplier)
                .flatMapMany(Flux::fromIterable);
        return blockingWrapper.subscribeOn(Schedulers.boundedElastic());
    }

}
