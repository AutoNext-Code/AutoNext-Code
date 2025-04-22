package com.autonext.code.autonext_server.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.autonext.code.autonext_server.models.Strike;

@Repository
public interface StrikeRepository extends JpaRepository<Strike, Integer> {
    
    @Query("SELECT s FROM Strike s WHERE s.user.id = :user_id AND s.active = TRUE")
    List<Strike> findActiveStrikeByUser(@Param("user_id") int user_id);

    @Query("SELECT s FROM Strike s WHERE s.active = TRUE")
    List<Strike> findActiveStrikes();
}
