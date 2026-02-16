package com.calendar.events.application.mappers;

import com.calendar.events.domain.models.Event;
import com.calendar.events.application.dtos.EventRequest;
import com.calendar.events.application.dtos.EventResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface EventRestMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "participantIds", ignore = true)
    @Mapping(target = "organizerId", ignore = true)
    Event toDomain(EventRequest request);

    EventResponse toResponse(Event domain);
}
