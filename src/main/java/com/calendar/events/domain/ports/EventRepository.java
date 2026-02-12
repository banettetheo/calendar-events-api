package com.calendar.events.domain.ports;

import com.calendar.events.domain.models.Event;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface EventRepository {
    Flux<Event> findAll();

    Mono<Event> findById(String id);

    Mono<Event> save(Event event);

    Mono<Void> deleteById(String id);
}
