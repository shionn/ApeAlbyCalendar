package ape.alby.db.dao;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import ape.alby.db.dbo.EventRegistration;

public interface UnregisterDao {

	@Select("SELECT COUNT(*) FROM event_register "
			+ "WHERE event = #{id} AND firstName = #{register.firstName} "
			+ "AND lastName = #{register.lastName} AND email = #{register.email} AND arrivalHour = #{register.arrivalHour}")
	int existsByCriteria(@Param("id") int id, @Param("register") EventRegistration registration);

	@Delete("DELETE FROM event_register "
			+ "WHERE event = #{id} AND firstName = #{register.firstName} "
			+ "AND lastName = #{register.lastName} AND email = #{register.email} AND arrivalHour = #{register.arrivalHour}")
	int deleteByCriteria(@Param("id") int id, @Param("register") EventRegistration registration);

}
