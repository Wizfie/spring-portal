package com.ms.springms.repository.team.memberTeam;

import com.ms.springms.entity.MemberTeam;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MemberTeamRepository extends JpaRepository <MemberTeam, Long> {
    List<MemberTeam> findAllByTeamId(Long teamId);

}
