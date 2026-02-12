package com.calendar.events.application.rest;

import com.calendar.events.domain.models.Event;
import com.calendar.events.domain.services.EventService;
import com.calendar.events.application.dtos.EventRequest;
import com.calendar.events.application.dtos.EventResponse;
import com.calendar.events.application.mappers.EventRestMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;

@RestController
@RequestMapping("events")
@RequiredArgsConstructor
public class EventController {

    private final EventService eventService;
    private final EventRestMapper mapper;

    @GetMapping
    public Flux<EventResponse> getAllEvents(@AuthenticationPrincipal Jwt jwt) {
        String userId = jwt.getClaimAsString("businessId");
        return eventService.getAllEvents().map(event -> toResponse(event, userId));
    }

    @GetMapping("/{id}")
    public Mono<EventResponse> getEventById(@PathVariable String id, @AuthenticationPrincipal Jwt jwt) {
        String userId = jwt.getClaimAsString("businessId");
        return eventService.getEventById(id).map(event -> toResponse(event, userId));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<EventResponse> createEvent(@Valid @RequestBody EventRequest request, @AuthenticationPrincipal Jwt jwt) {
        String userId = jwt.getClaimAsString("businessId");
        Event event = mapper.toDomain(request);
        event.setOrganizerId(userId);
        return eventService.createEvent(event).map(e -> toResponse(e, userId));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Mono<Void> deleteEvent(@PathVariable String id) {
        return eventService.deleteEvent(id);
    }

    @PostMapping("/{id}/subscribe")
    public Mono<EventResponse> toggleSubscription(@PathVariable String id, @AuthenticationPrincipal Jwt jwt) {
        String userId = jwt.getClaimAsString("businessId");
        return eventService.toggleSubscription(id, userId).map(event -> toResponse(event, userId));
    }

    private EventResponse toResponse(Event event, String userId) {
        EventResponse response = mapper.toResponse(event);
        if (event.getParticipantIds() != null && userId != null) {
            response.setSubscribed(event.getParticipantIds().contains(userId));
        } else {
            response.setSubscribed(false);
        }
        return response;
    }
}
