package com.ms.springms.repository.team;

import com.ms.springms.entity.Team;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TeamRepository  extends JpaRepository<Team , Long> {

    @Query("SELECT t FROM Team t JOIN FETCH t.members")
    List<Team> findAllWithMembers();
}
