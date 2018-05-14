package com.example.demo.infrastructure.configuration;

import com.example.demo.MockitoExtension;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.web.servlet.config.annotation.CorsRegistration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class WebMvcConfigurationUTest {

    private WebMvcConfiguration webMvcConfiguration;

    private CorsRegistry corsRegistry;
    private CorsRegistration corsRegistration;

    @BeforeEach
    void setUp() {
        webMvcConfiguration = new WebMvcConfiguration();

        corsRegistry = mock(CorsRegistry.class);
        when(corsRegistry.addMapping(anyString())).thenReturn(mock(CorsRegistration.class));

        corsRegistration = mock(CorsRegistration.class);
        when(corsRegistry.addMapping(anyString())).thenReturn(corsRegistration);
        when(corsRegistration.allowedOrigins(anyString())).thenReturn(corsRegistration);
        when(corsRegistration.allowedMethods(anyString(), anyString(), anyString(), anyString())).thenReturn(corsRegistration);
        when(corsRegistration.exposedHeaders(anyString())).thenReturn(corsRegistration);
    }

    @Nested
    class AddCorsMappingsShould {

        @Test
        void allow_all_paths() {
            // when
            webMvcConfiguration.addCorsMappings(corsRegistry);

            // then
            verify(corsRegistry).addMapping("/**");
        }

        @Test
        void allow_all_origins() {
            // when
            webMvcConfiguration.addCorsMappings(corsRegistry);

            // then
            verify(corsRegistration).allowedOrigins("*");
        }

        @Test
        void allow_get_post_head_and_option_methods() {
            // when
            webMvcConfiguration.addCorsMappings(corsRegistry);

            // then
            verify(corsRegistration).allowedMethods("GET", "POST", "HEAD", "OPTIONS");
        }

        @Test
        void expose_authorization_header() {
            // when
            webMvcConfiguration.addCorsMappings(corsRegistry);

            // then
            verify(corsRegistration).exposedHeaders("Authorization");
        }
    }
}
