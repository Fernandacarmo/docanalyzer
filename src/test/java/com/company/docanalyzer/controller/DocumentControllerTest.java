package com.company.docanalyzer.controller;

import com.company.docanalyzer.exception.ApiError;
import com.company.docanalyzer.model.Document;
import com.google.common.net.MediaType;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class DocumentControllerTest extends AbstractControllerTest {

    @Test
    void getDocuments() {
    }

    @Test
    void getDocument() {
    }

    @Test
    void upload() throws Exception {
        // given
        String uri = "/document/upload";
        byte[] file1 = new byte[]{77, 87, 53, 86, 73, 81, 53, 51};
        MockMultipartFile testFile1 = new MockMultipartFile("file", "file1.txt", MediaType.PLAIN_TEXT_UTF_8.toString(), file1);

        // when
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.multipart(uri)
                .file(testFile1)
                .param("email", "user1@gmail.com")
                .accept(org.springframework.http.MediaType.APPLICATION_JSON_VALUE))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();

        // then
        String content = mvcResult.getResponse().getContentAsString();
        Document document = super.mapFromJson(content, Document.class);
        assertTrue(document.getName().equals(testFile1.getOriginalFilename()));
    }

    @Test
    void upload_shouldThrowException_whenIncorrectFile() throws Exception {
        // given
        String uri = "/document/upload";
        byte[] file2 = new byte[]{77, 87, 53, 86, 73, 81, 53, 51};
        MockMultipartFile testFile2 = new MockMultipartFile("file", "file2.txt", MediaType.JPEG.toString(), file2);

        // when
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.multipart(uri)
                .file(testFile2)
                .param("email","user1@gmail.com")
                .accept(org.springframework.http.MediaType.APPLICATION_JSON_VALUE))
                .andDo(print())
                .andExpect(status().is4xxClientError())
                .andReturn();

        // then
        String content = mvcResult.getResponse().getContentAsString();
        ApiError actualResponse = mapFromJson(content, ApiError.class);
        assertTrue(actualResponse.getMessage().contains("Incorrect file type"));

        // given
        byte[] file3 = new byte[ 1024 * 1024 * 2 + 10];
        MockMultipartFile testFile3 = new MockMultipartFile("file", "file3.txt", MediaType.PLAIN_TEXT_UTF_8.toString(), file3);

        // when
        mvcResult = mvc.perform(MockMvcRequestBuilders.multipart(uri)
                .file(testFile3)
                .param("email","user1@gmail.com")
                .accept(org.springframework.http.MediaType.APPLICATION_JSON_VALUE))
                .andDo(print())
                .andExpect(status().is4xxClientError())
                .andReturn();

        // then
        content = mvcResult.getResponse().getContentAsString();
        actualResponse = mapFromJson(content, ApiError.class);
        assertTrue(actualResponse.getMessage().contains("Incorrect file size"));
    }

    @Test
    void getWordsByDoc() {
    }
}