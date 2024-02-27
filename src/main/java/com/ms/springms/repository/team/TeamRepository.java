package com.ms.springms.repository.team;

import com.ms.springms.entity.Team;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TeamRepository  extends JpaRepository<Team , Long> {
}
