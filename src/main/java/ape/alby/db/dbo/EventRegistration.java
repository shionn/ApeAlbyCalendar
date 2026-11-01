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

	public String getShortLabel() {
		String fn = firstName == null || firstName.isEmpty() ? "" : firstName.substring(0, 1).toUpperCase();
		String ln = lastName == null || lastName.isEmpty() ? "" : lastName.substring(0, 1).toUpperCase() + lastName.substring(1).toLowerCase();
		return fn + ". " + ln;
	}
}
