package com.goattech.deliverytracker;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.mockito.Mockito;
import org.springframework.security.oauth2.jwt.JwtDecoder;

@SpringBootTest
class DeliveryTrackerApplicationTests {

	@TestConfiguration
	static class TestConfig {
		@Bean
		public JwtDecoder jwtDecoder() {
			return Mockito.mock(JwtDecoder.class);
		}
	}

	@Test
	void contextLoads() {
	}

}
