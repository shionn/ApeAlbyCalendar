package ape.alby.controller;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ape.alby.db.dbo.EventRegistration;

public class EventRegistrationNormalizerTest {

	private EventRegistrationNormalizer normalizer;

	@BeforeEach
	public void setUp() {
		normalizer = new EventRegistrationNormalizer();
	}

	@Test
	public void testNormalizeWithHyphenatedLowercase() {
		EventRegistration input = EventRegistration.builder()
				.firstName("jean-paul")
				.lastName("dupont")
				.email("a@b.com")
				.arrivalHour(10).departureHour(12).comment("")
				.build();

		EventRegistration expected = EventRegistration.builder()
				.firstName("jean-paul")
				.lastName("dupont")
				.email("a@b.com")
				.arrivalHour(10).departureHour(12).comment("")
				.build();

		EventRegistration result = normalizer.normalize(input);
		assertThat(result.getFirstName()).isEqualTo(expected.getFirstName()).as("firstName attendu");
		assertThat(result.getLastName()).isEqualTo(expected.getLastName()).as("lastName attendu");
	}

	@Test
	public void testNormalizeWithAlreadyCapitalized() {
		EventRegistration input = EventRegistration.builder()
				.firstName("Jean-Pierre")
				.lastName("Dupont")
				.email("a@b.com")
				.arrivalHour(10).departureHour(12).comment("")
				.build();

		EventRegistration expected = EventRegistration.builder()
				.firstName("jean-pierre")
				.lastName("dupont")
				.email("a@b.com")
				.arrivalHour(10).departureHour(12).comment("")
				.build();

		EventRegistration result = normalizer.normalize(input);
		assertThat(result.getFirstName()).isEqualTo(expected.getFirstName()).as("firstName attendu");
		assertThat(result.getLastName()).isEqualTo(expected.getLastName()).as("lastName attendu");
	}

	@Test
	public void testNormalizeWithMixedCaseAndSpaces() {
		EventRegistration input = EventRegistration.builder()
				.firstName("jEaN pAuL")
				.lastName("mICHeL")
				.email("a@b.com")
				.arrivalHour(10).departureHour(12).comment("")
				.build();

		EventRegistration expected = EventRegistration.builder()
				.firstName("jean paul")
				.lastName("michel")
				.email("a@b.com")
				.arrivalHour(10).departureHour(12).comment("")
				.build();

		EventRegistration result = normalizer.normalize(input);
		assertThat(result.getFirstName()).isEqualTo(expected.getFirstName()).as("firstName attendu");
		assertThat(result.getLastName()).isEqualTo(expected.getLastName()).as("lastName attendu");
	}

	@Test
	public void testNormalizeWithMultipleSeparators() {
		EventRegistration input = EventRegistration.builder()
				.firstName("jean--paul  dupont")
				.lastName("martin")
				.email("a@b.com")
				.arrivalHour(10).departureHour(12).comment("")
				.build();

		EventRegistration expected = EventRegistration.builder()
				.firstName("jean--paul  dupont")
				.lastName("martin")
				.email("a@b.com")
				.arrivalHour(10).departureHour(12).comment("")
				.build();

		EventRegistration result = normalizer.normalize(input);
		assertThat(result.getFirstName()).isEqualTo(expected.getFirstName()).as("firstName attendu");
		assertThat(result.getLastName()).isEqualTo(expected.getLastName()).as("lastName attendu");
	}

	@Test
	public void testNormalizeLowercasesEmail() {
		EventRegistration input = EventRegistration.builder()
				.firstName("michel")
				.lastName("vaillant")
				.email("Michel.V@Exemple.COM")
				.arrivalHour(10).departureHour(12).comment("")
				.build();

		EventRegistration expected = EventRegistration.builder()
				.firstName("michel")
				.lastName("vaillant")
				.email("michel.v@exemple.com")
				.arrivalHour(10).departureHour(12).comment("")
				.build();

		EventRegistration result = normalizer.normalize(input);
		assertThat(result.getEmail()).isEqualTo(expected.getEmail()).as("email mis en minuscule");
	}

	@Test
	public void testNormalizeKeepsNullEmail() {
		EventRegistration input = EventRegistration.builder()
				.firstName("jean")
				.lastName("dupont")
				.email(null)
				.arrivalHour(10).departureHour(12).comment("")
				.build();

		EventRegistration result = normalizer.normalize(input);
		assertThat(result.getEmail()).as("email null conservé").isNull();
	}
}
