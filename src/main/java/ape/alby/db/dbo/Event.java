package ape.alby.db.dbo;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Event {

	private int id;
	private String name;
	private Date day;
	private int start, end;
	private List<EventRegistration> registrations;

	public List<EventPeriod> getPeriods() {
		List<EventRegistration> regs = registrations == null ? List.of() : registrations;
		return IntStream.range(start, end)
				.mapToObj(hour -> EventPeriod
						.builder()
						.period(hour)
						.registrations(regs.stream().filter(r -> r.getArrivalHour() == hour).toList())
						.build())
				.collect(Collectors.toList());
	}

	public int getMaxRegister() {
		return getPeriods().stream().map(p -> p.getRegistrations().size()).reduce(0, Integer::max);
	}

}
