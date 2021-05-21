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
class AuthorRestApiControllerTests {

    private final MockMvc mockMvc;

    @Autowired
    AuthorRestApiControllerTests(MockMvc mockMvc) {
        this.mockMvc = mockMvc;
    }

    @Test
    void authorById() throws Exception {
        mockMvc.perform(get("/api/authors/by-id").param("id", "3"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("The Hanoi Hilton")));
    }

    @Test
    void authorByFullName() throws Exception {
        mockMvc.perform(get("/api/authors/by-fullname")
                .param("firstName", "Ambros")
                .param("lastName", "Lardge"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("kepton2")));
    }
}