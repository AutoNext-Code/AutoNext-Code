package com.autonext.code.autonext_server.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.autonext.code.autonext_server.models.Strike;
import com.autonext.code.autonext_server.models.enums.Role;

@Repository
public interface StrikeRepository extends JpaRepository<Strike, Integer> {
    
    @Query("SELECT s FROM Strike s WHERE s.user.id = :user_id AND s.active = TRUE")
    List<Strike> findActiveStrikeByUser(@Param("user_id") int user_id);

    
    //Comprobar
    @Query("""
    SELECT s FROM Strike s
    WHERE s.user.role =:penalized
      AND s.active = true
      AND s.date = (
          SELECT MAX(s2.date) FROM Strike s2
          WHERE s2.user = s.user AND s2.active = true
      )
    """)
    List<Strike> findLatestActiveStrikesOfPenalizedUsers(@Param("penalized") Role penalized);



    @Query("SELECT s FROM Strike s WHERE s.active = TRUE")
    List<Strike> findActiveStrikes();
}
