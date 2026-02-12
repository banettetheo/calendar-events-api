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
    Event toDomain(EventRequest request);

    @Mapping(target = "subscribed", ignore = true)
    EventResponse toResponse(Event domain);
}
