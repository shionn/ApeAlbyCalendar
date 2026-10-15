package ape.alby.db.dbo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EventRegistration {

	private String firstName;
	private String lastName;
	private String email;
	private int arrivalHour;
	private int departureHour;
	private String comment;

}
