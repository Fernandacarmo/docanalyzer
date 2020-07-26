package com.company.docanalyzer.repository;

import com.company.docanalyzer.model.Team;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TeamRepository extends JpaRepository<Team, Long> {

    List<Team> findBy();

    @Query("select distinct t from Team t inner join fetch t.users u")
    List<Team> findUsersByTeam();
}
