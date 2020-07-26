package com.company.docanalyzer.repository;

import com.company.docanalyzer.model.Document;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.Duration;
import java.time.Instant;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@DataJpaTest
class DocumentRepositoryTest {

    @Autowired
    private DocumentRepository documentRepository;

    @Test
    void findBy_shouldFInd4FDocs_when1MoreAdded() {
        // given
        Document doc = Document.builder().name("sample4.txt").build();
        doc = documentRepository.save(doc);

        // when
        List<Document> docs = documentRepository.findBy();

        // then
        assertEquals(docs.size(), 4);
        assertEquals(docs.get(0).getName(), "sample1.txt");
        assertEquals(docs.get(1).getName(), "sample2.txt");
        assertEquals(docs.get(2).getName(), "sample3.txt");
        assertEquals(docs.get(3).getName(), "sample4.txt");
    }

    @Test
    void findAllDocumentsByTeam_shouldFind2Docs_whenTeam1() {
        // when
        List<Document> docs = documentRepository.findAllDocumentsByTeam("team1");

        // then
        assertEquals(docs.size(), 2);
        assertEquals(docs.get(0).getName(), "sample1.txt");
        assertEquals(docs.get(1).getName(), "sample2.txt");
    }

    @Test
    void findAllByDateBetween() {
        // when
        List<Document> docs = documentRepository.findAllByDateBetween(Date.from(Instant.now().minus(Duration.ofDays(2))), new Date());

        // then
        assertEquals(docs.size(), 1);
        assertEquals(docs.get(0).getName(), "sample3.txt");
    }
}