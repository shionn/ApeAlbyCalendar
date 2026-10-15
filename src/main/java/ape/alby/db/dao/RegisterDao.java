package ape.alby.db.dao;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import ape.alby.db.dbo.Event;
import ape.alby.db.dbo.EventRegistration;

public interface RegisterDao {

	@Select("SELECT * FROM event WHERE id = #{id}")
	Event readEventById(int id);

	@Insert("""
			INSERT INTO event_register
			(event, firstName, lastName, email, arrivalHour, departureHour, comment)
			VALUES
			( #{id}, #{register.firstName}, #{register.lastName}, #{register.email}, 
			#{register.arrivalHour}, #{register.departureHour}, #{register.comment} )
			""")
	int register(@Param("id") int id, @Param("register") EventRegistration registration);

}
