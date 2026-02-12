package com.calendar.events.infrastructure.persistence.adapters;

import com.calendar.events.domain.models.Event;
import com.calendar.events.domain.ports.EventRepository;
import com.calendar.events.infrastructure.persistence.mappers.EventPersistenceMapper;
import com.calendar.events.infrastructure.persistence.repositories.ReactiveEventMongoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class MongoEventRepositoryAdapter implements EventRepository {

    private final ReactiveEventMongoRepository mongoRepository;
    private final EventPersistenceMapper mapper;

    @Override
    public Flux<Event> findAll() {
        return mongoRepository.findAll().map(mapper::toDomain);
    }

    @Override
    public Mono<Event> findById(String id) {
        return mongoRepository.findById(id).map(mapper::toDomain);
    }

    @Override
    public Mono<Event> save(Event event) {
        return mongoRepository.save(mapper.toEntity(event)).map(mapper::toDomain);
    }

    @Override
    public Mono<Void> deleteById(String id) {
        return mongoRepository.deleteById(id);
    }
}
