package ape.alby.db.dao;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

public interface UnregisterDao {

	@Select("SELECT COUNT(*) FROM event_register "
			+ "WHERE event = #{id} AND firstName = #{firstName} "
			+ "AND lastName = #{lastName} AND email = #{email} AND arrivalHour = #{arrivalHour}")
	int existsByCriteria(@Param("id") int id, @Param("firstName") String firstName,
			@Param("lastName") String lastName, @Param("email") String email, @Param("arrivalHour") int arrivalHour);

	@Delete("DELETE FROM event_register "
			+ "WHERE event = #{id} AND firstName = #{firstName} "
			+ "AND lastName = #{lastName} AND email = #{email} AND arrivalHour = #{arrivalHour}")
	int deleteByCriteria(@Param("id") int id, @Param("firstName") String firstName,
			@Param("lastName") String lastName, @Param("email") String email, @Param("arrivalHour") int arrivalHour);

}
