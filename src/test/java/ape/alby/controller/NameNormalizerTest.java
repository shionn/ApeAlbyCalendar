package ape.alby.controller;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

import ape.alby.db.dbo.EventRegistration;

public class NameNormalizerTest {

	@Test
	public void testCapitalizeSingleWord() {
		String result = new EventRegistrationNormalizer().capitalizeWords("julie");

		assertThat(result)
				.as("un mot 'julie' doit devenir 'Julie'")
				.isEqualTo("Julie");
	}

	@Test
	public void testCapitalizeMultipleWords() {
		String result = new EventRegistrationNormalizer().capitalizeWords("jean paul");

		assertThat(result)
				.as("deux mots 'jean paul' doivent devenir 'Jean Paul'")
				.isEqualTo("Jean Paul");
	}

	@Test
	public void testCapitalizePreservesHyphenCase() {
		String result = new EventRegistrationNormalizer().capitalizeWords("jean-paul");

		assertThat(result)
				.as("nom hyphené 'jean-paul' doit devenir 'Jean-Paul'")
				.isEqualTo("Jean-Paul");
	}

	@Test
	public void testCapitalizePreservesExistingCasing() {
		String result = new EventRegistrationNormalizer().capitalizeWords("Jean-Pierre");

		assertThat(result)
				.as("casse existante 'Jean-Pierre' doit être préservée")
				.isEqualTo("Jean-Pierre");
	}

	@Test
	public void testCapitalizeNullReturnsNull() {
		String result = new EventRegistrationNormalizer().capitalizeWords(null);

		assertThat(result)
				.as("capitalizeWords(null) renvoie null")
				.isNull();
	}

	@Test
	public void testCapitalizeTrimsWhitespace() {
		String result = new EventRegistrationNormalizer().capitalizeWords("  jean   paul  ");

		assertThat(result)
				.as("espaces excédentaires doivent être supprimés -> 'Jean Paul'")
				.isEqualTo("Jean Paul");
	}

	@Test
	public void testNormalizeCapitalizesNames() {
		EventRegistration form = EventRegistration.builder()
				.firstName("jean")
				.lastName("paul")
				.email("jean@example.com")
				.arrivalHour(10)
				.departureHour(12)
				.build();
		EventRegistration result = new EventRegistrationNormalizer().normalize(form);

		assertThat(result.getFirstName())
				.as("firstName 'jean' doit devenir 'Jean'")
				.isEqualTo("Jean");
		assertThat(result.getLastName())
				.as("lastName 'paul' doit devenir 'Paul'")
				.isEqualTo("Paul");
	}

	@Test
	public void testNormalizePreservesOtherFields() {
		EventRegistration form = EventRegistration.builder()
				.firstName("jean-paul")
				.lastName("de-montfort")
				.email("jean@example.com")
				.arrivalHour(10)
				.departureHour(12)
				.comment("ok")
				.build();
		EventRegistration result = new EventRegistrationNormalizer().normalize(form);

		assertThat(result.getFirstName())
				.as("firstName 'jean-paul' doit devenir 'Jean-Paul'")
				.isEqualTo("Jean-Paul");
		assertThat(result.getLastName())
				.as("lastName 'de-montfort' doit devenir 'De-Montfort'")
				.isEqualTo("De-Montfort");
		assertThat(result.getEmail())
				.as("email doit être préservé")
				.isEqualTo("jean@example.com");
		assertThat(result.getArrivalHour())
				.as("arrivalHour doit être préservé")
				.isEqualTo(10);
		assertThat(result.getDepartureHour())
				.as("departureHour doit être préservé")
				.isEqualTo(12);
		assertThat(result.getComment())
				.as("comment doit être préservé")
				.isEqualTo("ok");
	}
}
