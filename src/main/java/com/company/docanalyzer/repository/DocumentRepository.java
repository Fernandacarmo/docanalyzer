package com.company.docanalyzer.repository;

import com.company.docanalyzer.model.Document;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface DocumentRepository extends JpaRepository<Document, Long> {

    List<Document> findBy();

    @Query("from Document d inner join d.user u inner join fetch u.teams t where t.name = ?1")
    List<Document> findAllDocumentsByTeam(String teamName);

    List<Document> findAllByDateBetween(Date fromDate, Date toDate);

}

