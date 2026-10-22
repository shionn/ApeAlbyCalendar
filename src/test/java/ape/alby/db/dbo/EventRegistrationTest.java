package ape.alby.db.dbo;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

public class EventRegistrationTest {

	@Test
	public void testGetShortLabelFormatsFirstAndLastName() {
		EventRegistration registration = EventRegistration.builder()
				.firstName("jean")
				.lastName("paul")
				.build();

		assertThat(registration.getShortLabel())
				.as("le label joint les deux noms avec un espace puis un point")
				.isEqualTo("jean paul.");
	}

	@Test
	public void testGetShortLabelWithNullFirstName() {
		EventRegistration registration = EventRegistration.builder()
				.firstName(null)
				.lastName("paul")
				.build();

		assertThat(registration.getShortLabel())
				.as("un nom firstName null est rendu 'null' par String.format")
				.isEqualTo("null paul.");
	}

	@Test
	public void testGetShortLabelWithNullLastName() {
		EventRegistration registration = EventRegistration.builder()
				.firstName("jean")
				.lastName(null)
				.build();

		assertThat(registration.getShortLabel())
				.as("un nom lastName null est rendu 'null' par String.format")
				.isEqualTo("jean null.");
	}

	@Test
	public void testGetShortLabelWithNullNames() {
		EventRegistration registration = EventRegistration.builder()
				.firstName(null)
				.lastName(null)
				.build();

		assertThat(registration.getShortLabel())
				.as("deux noms null sont rendus 'null' par String.format")
				.isEqualTo("null null.");
	}

	@Test
	public void testBuilderPopulatesAllFields() {
		EventRegistration registration = EventRegistration.builder()
				.firstName("jean-paul")
				.lastName("de-montfort")
				.email("jean@example.com")
				.arrivalHour(10)
				.departureHour(12)
				.comment("ok")
				.build();

		assertThat(registration.getFirstName())
				.as("firstName est conservé tel quel")
				.isEqualTo("jean-paul");
		assertThat(registration.getLastName())
				.as("lastName est conservé tel quel")
				.isEqualTo("de-montfort");
		assertThat(registration.getEmail())
				.as("email est conservé")
				.isEqualTo("jean@example.com");
		assertThat(registration.getArrivalHour())
				.as("arrivalHour est conservé")
				.isEqualTo(10);
		assertThat(registration.getDepartureHour())
				.as("departureHour est conservé")
				.isEqualTo(12);
		assertThat(registration.getComment())
				.as("comment est conservé")
				.isEqualTo("ok");
	}
}
