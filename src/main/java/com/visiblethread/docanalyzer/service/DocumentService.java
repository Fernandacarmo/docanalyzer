package com.visiblethread.docanalyzer.service;

import com.visiblethread.docanalyzer.exception.UserNotFoundException;
import com.visiblethread.docanalyzer.model.Document;
import com.visiblethread.docanalyzer.model.User;
import com.visiblethread.docanalyzer.repository.DocumentRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.CharArrayReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Timestamp;
import java.util.Arrays;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@Slf4j
public class DocumentService {

    private static final List<String> EXCLUDED_WORDS = List.of("the", "me", "you", "i", "of", "and", "a", "we");

    @Autowired
    private DocumentRepository documentRepository;

    @Autowired
    private UserService userService;

    public List<Document> getDocuments() {
        return documentRepository.findBy();
    }

    public Document getDocument(Long id) {
        return documentRepository.findById(id).orElseThrow();
    }

    public Map<String, Integer> getWordsByDoc(Long id, boolean exclude, Integer limit) {
        Map<String, Integer> wordCountSorted = new HashMap<>();
        Optional<Document> doc = documentRepository.findById(id);
        if (doc.isPresent()) {

            char[] file = doc.get().getFile();
            CharArrayReader reader = new CharArrayReader(file);
            BufferedReader br = new BufferedReader(reader);
            String line;
            try {
                Map<String, Integer> wordCount = new HashMap<>();
                while ((line = br.readLine()) != null) {
                    List<String> words;
                    Stream<String> stream =
                            Arrays.stream(line.replaceAll("[^a-zA-Z ]", "").toLowerCase().split("\\s+"));
                    if (exclude) {
                        stream = stream.filter(word -> !EXCLUDED_WORDS.contains(word.toLowerCase()));
                    }
                    words = stream.collect(Collectors.toList());

                    processWordCount(wordCount, words);
                }

                wordCountSorted = sortByValue(wordCount, limit);
            } catch (IOException e) {
                log.error("Error while reading document {}", doc.get().getName());
            }
        }
        return wordCountSorted;
    }

    private void processWordCount(Map<String, Integer> wordCount, List<String> words) {
        words.stream().forEach(word -> {
            Integer count = wordCount.get(word.toLowerCase().trim());
            if (count != null) {
                wordCount.put(word.toLowerCase(), ++count);
            } else {
                wordCount.put(word.toLowerCase(), 1);
            }
        });
    }

    private Map<String, Integer> sortByValue(Map<String, Integer> unsortMap, Integer limit) {
        List<Map.Entry<String, Integer>> list = new LinkedList<>(unsortMap.entrySet());

        // Sorting the list based on values
        return list.stream()
                .sorted((o1, o2) -> o2.getValue().compareTo(o1.getValue()) == 0 ?
                        o1.getKey().compareTo(o2.getKey()) :
                        o2.getValue().compareTo(o1.getValue()))
                .limit(limit != null? limit: list.size())
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (a, b) -> b, LinkedHashMap::new));
    }

    @Transactional
    public Document uploadDocument(String email, MultipartFile document) throws UserNotFoundException {

        User user = userService.findByEmail(email);

        try (InputStream in = document.getInputStream()) {
            InputStreamReader reader = new InputStreamReader(in);
            BufferedReader br = new BufferedReader(reader);
            String line;
            int wordCount = 0;
            StringBuilder sb = new StringBuilder();
            while ((line = br.readLine()) != null) {
                String[] words = line.replaceAll("[^a-zA-Z ]", "").split("\\s+");
                wordCount += words.length;
                sb.append(line);
            }

            return saveDocument(user, sb, document.getOriginalFilename(), wordCount);

        } catch (Exception ex) {
            log.error("Error while uploading document {}", ex);
        }
        return null;
    }

    public Document saveDocument(User user, StringBuilder sb, String fileName, int words) {
        Document doc = new Document();
        doc.setUser(user);
        doc.setDate(new Timestamp(new GregorianCalendar().getTime().getTime()));
        doc.setFile(sb.toString().toCharArray());
        doc.setName(fileName);
        doc.setWords(words);
        return documentRepository.save(doc);
    }

    public List<Document> getDocumentsByTeam(String teamName) {
        return documentRepository.findAllDocumentsByTeam(teamName);
    }

    public List<Document> getDocumentsByDate(Date fromDate, Date toDate) {
        return documentRepository.findAllByDateBetween(fromDate, toDate);
    }
}
