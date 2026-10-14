package ape.alby.db.dbo;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class EventPeriod {

	private int period;

	public String getLabel() {
		return String.format("%dh-%dh", period, period + 1);
	}

}
