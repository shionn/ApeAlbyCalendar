package ape.alby.db.dbo;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

public class EventPeriodsTest {

	@Test
	public void testGetPeriodsCouvreLaPlageInclusifExclusif() {
		Event event = Event.builder().start(5).end(7).registrations(Collections.emptyList()).build();
		List<EventPeriod> periods = event.getPeriods();

		assertThat(periods)
				.as("getPeriods(start=5, end=7) doit renvoyer [EventPeriod(5), EventPeriod(6)]")
				.hasSize(2)
				.extracting(EventPeriod::getPeriod)
				.containsExactly(5, 6);
	}

	@Test
	public void testGetMaxRegisterRetourneLeMaxParPeriodeEtNonLaSomme() {
		EventRegistration r1 = EventRegistration.builder().arrivalHour(5).build();
		EventRegistration r2 = EventRegistration.builder().arrivalHour(6).build();
		EventRegistration r3 = EventRegistration.builder().arrivalHour(7).build();
		Event event = Event.builder().start(5).end(8).registrations(java.util.List.of(r1, r2, r3)).build();

		assertThat(event.getMaxRegister())
				.as("périodes [5:1, 6:1, 7:0] -> max = 1, pas la somme 3")
				.isEqualTo(1);
	}


	@Test
	public void testGetPeriodsRegistrationsNullNeLancePasDeNPE() {
		Event event = Event.builder().start(5).end(7).build();
		assertThat(event.getPeriods())
				.as("registrations == null : getPeriods ne doit pas lancer de NPE")
				.hasSize(2)
				.extracting(EventPeriod::getPeriod)
				.containsExactly(5, 6);
	}


	@Test
	public void testGetPeriodsRangeValideSingleton() {
		Event event = Event.builder().start(3).end(3).build();
		event.setStart(3);
		event.setEnd(3);

		assertThat(event.getPeriods())
				.as("start==end (3==3): la liste doit être vide")
				.isEmpty();
	}

	@Test
	public void testGetPeriodsRangeInverseEstVide() {
		Event event = Event.builder().start(9).end(4).build();
		event.setStart(9);
		event.setEnd(4);

		assertThat(event.getPeriods())
				.as("start > end (9 > 4): la liste doit être vide")
				.isEmpty();
	}
}
