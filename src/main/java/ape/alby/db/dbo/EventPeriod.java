package ape.alby.db.dbo;

import java.util.List;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class EventPeriod {

	private int period;
	private List<EventRegistration> registrations;

	public String getLabel() {
		return String.format("%dh-%dh", period, period + 1);
	}

}
