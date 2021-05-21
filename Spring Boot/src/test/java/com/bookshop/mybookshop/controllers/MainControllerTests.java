package com.bookshop.mybookshop.controllers;

import static org.hamcrest.CoreMatchers.containsString;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.formLogin;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.authenticated;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.xpath;

@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource("/application-test.properties")
class MainControllerTests {

    private final MockMvc mockMvc;

    @Autowired
    MainControllerTests(MockMvc mockMvc) {
        this.mockMvc = mockMvc;
    }

    @Test
    public void mainPageAccessTest() throws Exception {
        mockMvc.perform(get("/"))
                .andDo(print())
                .andExpect(content().string(containsString("Книги по тегам")))
                .andExpect(status().isOk());
    }

    @Test
    public void accessOnlyAuthorizedPageFailTest() throws Exception {
        mockMvc.perform(get("/my"))
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/401"));
    }

    @Test
    public void correctLoginTest() throws Exception {
        mockMvc.perform(formLogin("/signin").user("test@mail.com").password("12345678"))
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/"));
    }

    @Test
    @WithUserDetails("test@mail.com")
    public void testAuthenticatedAccessToProfilePage() throws Exception {
        mockMvc.perform(get("/profile"))
                .andDo(print())
                .andExpect(authenticated())
                .andExpect(xpath("/html/body/header/div[1]/div/div/div[3]/div/a[4]/span[1]")
                        .string("test@mail.com"));
    }

    @Test
    public void testSearchQuery() throws Exception {
        mockMvc.perform(get("/search/Roger"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(xpath("/html/body/div/div/main/div[2]/div/div[1]/div[2]/strong/a")
                        .string("Roger Dodger"));
    }

    @Test
    public void testSpecificTagPageAccess() throws Exception {
        mockMvc.perform(get("/tags/Organized"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(xpath("/html/body/div/div/main/div/div[1]/h1")
                        .string("Organized"));
    }

    @Test
    public void testSpecificGenrePageAccess() throws Exception {
        mockMvc.perform(get("/genres/Genre1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(xpath("/html/body/div/div/main/div/div/div[1]/div[2]/strong/a")
                        .string("Thunderpants"));
    }

    @Test
    public void testAuthorBooksPageAccess() throws Exception {
        mockMvc.perform(get("/authors/Campes_Evin"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(xpath("/html/body/div/div/main/h1/text()")
                        .string("Campes Evin"));
    }

    @Test
    public void testBookPageAccess() throws Exception {
        mockMvc.perform(get("/book/kepton2"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("The Hanoi Hilton")));
    }

    @Test
    public void testDownloadBookFile() throws Exception {
        mockMvc.perform(get("/book/download/ololo1111ololo"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.header().string(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_PDF.toString()))
                .andExpect(MockMvcResultMatchers.header().string(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=Whity.pdf"));
    }

    @Test
    @WithUserDetails("test@mail.com")
    public void testSaveNewReview() throws Exception {
        mockMvc.perform(post("/book/mtorricina4d/review/save")
                .param("comment", "testtesttest")
                .param("authorName", "test@mail.com"))
                .andDo(print())
                .andExpect(status().is3xxRedirection());

        mockMvc.perform(get("/book/mtorricina4d"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("testtesttest")));
    }
}