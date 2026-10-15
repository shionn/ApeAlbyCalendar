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
		return IntStream.range(start, end)
				.mapToObj(i -> EventPeriod.builder().period(i).build())
				.collect(Collectors.toList());
	}

}
