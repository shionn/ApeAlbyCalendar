package ape.alby.db.dbo;

import lombok.Data;

@Data
public class EventRegistration {

	private String firstName;
	private String lastName;
	private String email;
	private int arrivalHour;
	private int departureHour;
	private String comment;

}
