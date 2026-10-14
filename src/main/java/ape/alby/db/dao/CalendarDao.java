package ape.alby.db.dao;

import org.apache.ibatis.annotations.Select;

import ape.alby.db.dbo.Event;

public interface CalendarDao {

	@Select("SELECT * FROM event WHERE day IN ( SELECT max(day) FROM event )")
	public Event readCurrentEvent();

}
