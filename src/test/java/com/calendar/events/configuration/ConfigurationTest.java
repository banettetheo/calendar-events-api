package com.calendar.events.configuration;

import com.calendar.events.domain.ports.EventRepository;
import com.calendar.events.domain.services.EventService;
import org.junit.jupiter.api.Test;
import org.springframework.web.reactive.config.PathMatchConfigurer;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

class ConfigurationTest {

    @Test
    void eventApplicationConfig_shouldCreateEventService() {
        EventApplicationConfig config = new EventApplicationConfig();
        EventRepository repository = mock(EventRepository.class);
        EventService service = config.eventService(repository);
        assertNotNull(service);
    }

    @Test
    void webConfig_shouldConfigurePathMatching() {
        WebConfig config = new WebConfig();
        PathMatchConfigurer configurer = mock(PathMatchConfigurer.class);
        config.configurePathMatching(configurer);
        verify(configurer).addPathPrefix(any(), any());
    }
}
