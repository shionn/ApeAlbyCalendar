package ape.alby.controller;

import java.util.Arrays;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import ape.alby.db.dbo.EventRegistration;

@Component
public class EventRegistrationNormalizer {
	public EventRegistration normalize(EventRegistration registration) {
		registration.setFirstName(capitalizeWords(registration.getFirstName()));
		registration.setLastName(capitalizeWords(registration.getLastName()));
		return registration;
	}

	public String capitalizeWords(String value) {
		return Arrays
				.stream(value.split("\\s+"))
				.filter(word -> !word.isEmpty())
				.map(this::capitalizeWord)
				.collect(Collectors.joining(" "));
	}

	private String capitalizeWord(String token) {
		String[] parts = token.split("-+");
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < parts.length; i++) {
			if (i > 0) {
				sb.append('-');
			}
			String part = parts[i];
			if (!part.isEmpty()) {
				sb.append(Character.toUpperCase(part.charAt(0)));
sb.append(part.substring(1).toLowerCase());
			}
		}
		return sb.toString();
	}
}
