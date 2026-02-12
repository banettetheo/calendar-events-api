package com.calendar.events.infrastructure.persistence.mappers;

import com.calendar.events.domain.models.Event;
import com.calendar.events.infrastructure.persistence.entities.EventEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface EventPersistenceMapper {

    Event toDomain(EventEntity entity);

    EventEntity toEntity(Event domain);
}
