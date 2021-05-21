package com.bookshop.mybookshop.controllers.rest;

import static org.hamcrest.CoreMatchers.containsString;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource("/application-test.properties")
class BookRestApiControllerTests {

    private final MockMvc mockMvc;

    @Autowired
    BookRestApiControllerTests(MockMvc mockMvc) {
        this.mockMvc = mockMvc;
    }

    @Test
    void bookById() throws Exception {
        mockMvc.perform(get("/api/books/by-id")
                .param("id", "1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Bridge, The (Most)")));
    }

    @Test
    void booksByTitle() throws Exception {
        mockMvc.perform(get("/api/books/by-title")
                .param("title", "Bridge"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("chandrik0")));
    }

    @Test
    void booksByPrice() throws Exception {
        mockMvc.perform(get("/api/books/by-price")
                .param("price", "804"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Campes Evin")));
    }

    @Test
    void bestsellers() throws Exception {
        mockMvc.perform(get("/api/books/bestsellers"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("514 elements")));
    }
}