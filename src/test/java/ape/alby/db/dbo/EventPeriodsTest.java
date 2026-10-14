package ape.alby.db.dbo;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.Test;

public class EventPeriodsTest {

	@Test
	public void testGetPeriodsCouvreLaPlageInclusifExclusif() {
		Event event = Event.builder().start(5).end(7).build();
		List<EventPeriod> periods = event.getPeriods();

		assertThat(periods)
				.as("getPeriods(start=5, end=7) doit renvoyer [EventPeriod(5), EventPeriod(6)]")
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
