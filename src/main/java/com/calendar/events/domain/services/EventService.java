package com.calendar.events.domain.services;

import com.calendar.events.domain.models.Event;
import com.calendar.events.domain.ports.EventRepository;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@RequiredArgsConstructor
public class EventService {

    private final EventRepository eventRepository;

    public Flux<Event> getAllEvents() {
        return eventRepository.findAll();
    }

    public Mono<Event> getEventById(String id) {
        return eventRepository.findById(id);
    }

    public Mono<Event> createEvent(Event event) {
        return eventRepository.save(event);
    }

    public Mono<Void> deleteEvent(String id) {
        return eventRepository.deleteById(id);
    }

    public Mono<Event> toggleSubscription(String id, String userId) {
        return eventRepository.findById(id)
                .flatMap(event -> {
                    List<String> participants = event.getParticipantIds();
                    if (participants.contains(userId)) {
                        participants.remove(userId);
                    } else {
                        participants.add(userId);
                    }
                    return eventRepository.save(event);
                });
    }

    public Flux<Event> getSubscribedEvents(String userId) {
        return eventRepository.findByParticipantId(userId);
    }

    public Flux<Event> getFeedEvents(String userId) {
        return eventRepository.findByParticipantIdNot(userId);
    }
}
