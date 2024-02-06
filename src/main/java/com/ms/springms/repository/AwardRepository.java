package com.ms.springms.repository;

import com.ms.springms.entity.Awards;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AwardRepository extends JpaRepository<Awards ,Long> {

}
