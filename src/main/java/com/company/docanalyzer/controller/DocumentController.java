package com.company.docanalyzer.controller;

import com.company.docanalyzer.exception.IncorrectFileException;
import com.company.docanalyzer.model.Document;
import com.google.common.net.MediaType;
import com.company.docanalyzer.exception.UserNotFoundException;
import com.company.docanalyzer.service.DocumentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;
import java.util.Set;

@RestController
public class DocumentController {

    private static final long MAXIMUM_SIZE = 1024 * 1024 * 2;
    private static final Set<String> ACCEPTED_CONTENT_TYPES = Set.of(
            MediaType.PLAIN_TEXT_UTF_8.toString(),
            MediaType.ANY_TEXT_TYPE.toString(),
            MediaType.PLAIN_TEXT_UTF_8.withoutParameters().toString());

    @Autowired
    private DocumentService documentService;

    @GetMapping(value = "/documents")
    public List<Document> getDocuments() {
        return documentService.getDocuments();
    }

    @GetMapping(value = "/document/{id}")
    public Document getDocument(@PathVariable("id")  Long id) {
        return documentService.getDocument(id);
    }

    @PostMapping(value = {"/document/upload"})
    public Document upload(@RequestParam(value = "email", required = true) String email,
                           @RequestPart(value = "file", required = true) MultipartFile document)
            throws UserNotFoundException, IncorrectFileException {

        String contentType = document.getContentType();
        long size = document.getSize();

        if (size <= MAXIMUM_SIZE) {
            if (ACCEPTED_CONTENT_TYPES.contains(contentType)) {

                return documentService.uploadDocument(email, document);
            } else {
                throw new IncorrectFileException("Incorrect file type: " + contentType);
            }
        } else {
            throw new IncorrectFileException("Incorrect file size: " + size);
        }
    }

    @GetMapping(value = "/document/{id}/words")
    public Map<String, Integer> getWordsByDoc(@PathVariable("id") Long id,
                                              @RequestParam(value = "excludeWords", required = false, defaultValue = "false") boolean exclude,
                                              @RequestParam(value = "limit", required = false) Integer limit) {
        Map<String, Integer> map = documentService.getWordsByDoc(id, exclude, limit);
        return map;
    }
}
