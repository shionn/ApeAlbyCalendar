package ape.alby.controller;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

import ape.alby.db.dbo.EventRegistration;

public class NameNormalizerTest {

	@Test
	public void testNormalizeLowercasesNames() {
		EventRegistration form = EventRegistration.builder()
				.firstName("jean")
				.lastName("paul")
				.email("jean@example.com")
				.arrivalHour(10)
				.departureHour(12)
				.build();
		EventRegistration result = new EventRegistrationNormalizer().normalize(form);

		assertThat(result.getFirstName())
				.as("firstName 'jean' doit être en minuscule 'jean'")
				.isEqualTo("jean");
		assertThat(result.getLastName())
				.as("lastName 'paul' doit être en minuscule 'paul'")
				.isEqualTo("paul");
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
				.as("firstName 'jean-paul' en minuscule 'jean-paul'")
				.isEqualTo("jean-paul");
		assertThat(result.getLastName())
				.as("lastName 'de-montfort' en minuscule 'de-montfort'")
				.isEqualTo("de-montfort");
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
