package com.calendar.events.infrastructure.persistence.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "events")
public class EventEntity {
    @Id
    private String id;
    private String title;
    private String organizerId;
    private LocalDateTime date;
    private String location;
    private String image;
    private String description;
    @Builder.Default
    private List<String> participantIds = new ArrayList<>();
}
