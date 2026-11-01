package ape.alby.controller;

import org.springframework.stereotype.Component;

import ape.alby.db.dbo.EventRegistration;

@Component
public class EventRegistrationNormalizer {
	public EventRegistration normalize(EventRegistration registration) {
		registration.setFirstName(lowercase(registration.getFirstName()));
		registration.setLastName(lowercase(registration.getLastName()));
		registration.setEmail(lowercase(registration.getEmail()));
		return registration;
	}

	private String lowercase(String value) {
		return value == null ? null : value.toLowerCase();
	}
}
