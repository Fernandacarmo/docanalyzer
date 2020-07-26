package com.company.docanalyzer.controller;

import com.company.docanalyzer.exception.ApiError;
import com.company.docanalyzer.model.User;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.Instant;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class UserControllerTest extends AbstractControllerTest {

    @Test
    void getUsers() throws Exception {
        // when
        String uri = "/users";
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(uri)
                .accept(MediaType.APPLICATION_JSON_VALUE))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();

        // then
        String content = mvcResult.getResponse().getContentAsString();
        User[] users = super.mapFromJson(content, User[].class);
        assertEquals(users.length, 3);
        assertEquals(users[0].getEmail(), "user1@gmail.com");
        assertEquals(users[1].getEmail(), "user2@gmail.com");
        assertEquals(users[2].getEmail(), "user3@gmail.com");
    }

    @Test
    void getUserByEmail() throws Exception {
        // when
        String uri = "/user/user1@gmail.com";
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(uri)
                .accept(MediaType.APPLICATION_JSON_VALUE))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();

        // then
        String content = mvcResult.getResponse().getContentAsString();
        User user = super.mapFromJson(content, User.class);
        assertEquals(user.getEmail(), "user1@gmail.com");
    }

    @Test
    void getUserByEmail_shouldThrowException_whenUserNotFound() throws Exception {
        // given
        String uri = "/user/wrong@gmail.com";
        ApiError apiError = new ApiError(HttpStatus.NOT_FOUND);
        apiError.setMessage("User not found with email wrong@gmail.com");

        // when
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(uri)
                .accept(MediaType.APPLICATION_JSON_VALUE))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andReturn();

        // then
        String actualResponseBody = mvcResult.getResponse().getContentAsString();
        ApiError actualResponse = mapFromJson(actualResponseBody, ApiError.class);
        assertTrue(actualResponse.getMessage().equals(apiError.getMessage()));
        assertTrue(actualResponse.getStatus().equals(apiError.getStatus()));

    }

    @Test
    void filterUsers() throws Exception {
        //given
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd");
        String fromDate = simpleDateFormat.format(Date.from(Instant.now().minus(Duration.ofDays(4))));
        String toDate = simpleDateFormat.format(Date.from(Instant.now()));

        // when
        String uri = "/user/upload";
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(uri)
                .param("fromDate", fromDate)
                .param("toDate", toDate)
                .accept(MediaType.APPLICATION_JSON_VALUE))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();

        // then
        String content = mvcResult.getResponse().getContentAsString();
        User[] users = super.mapFromJson(content, User[].class);
        assertEquals(users.length, 2);
        assertEquals(users[0].getEmail(), "user1@gmail.com");
        assertEquals(users[1].getEmail(), "user2@gmail.com");
    }
}