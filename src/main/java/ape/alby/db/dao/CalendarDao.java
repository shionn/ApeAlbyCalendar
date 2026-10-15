package ape.alby.db.dao;

import java.util.List;

import org.apache.ibatis.annotations.Many;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import ape.alby.db.dbo.Event;
import ape.alby.db.dbo.EventRegistration;

public interface CalendarDao {

	@Select("SELECT * FROM event WHERE day IN ( SELECT max(day) FROM event )")
	@Results({ @Result(column = "id", property = "id"),
			@Result(column = "id", property = "registrations", many = @Many(select = "readRegistration")) })
	Event readCurrentEvent();

	@Select("SELECT * FROM event_register WHERE event = #{event}")
	List<EventRegistration> readRegistration(int event);

}
