package com.bookshop.mybookshop.controllers;

import javax.servlet.http.Cookie;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.cookie;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.xpath;

@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource("/application-test.properties")
class BookShopStatusControllerTests {

    private final MockMvc mockMvc;

    @Autowired
    BookShopStatusControllerTests(MockMvc mockMvc) {
        this.mockMvc = mockMvc;
    }

    @Test
    void handleCartRequest() throws Exception {
        mockMvc.perform(get("/book/cart")
                .cookie(new Cookie("cartContents", "ksturley1")))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(xpath("/html/body/div/div/main/form/div[1]/div/div[2]/div[1]/div[2]/a")
                        .string("Whity"));
    }

    @Test
    void handlePostponedListRequest() throws Exception {
        mockMvc.perform(get("/book/postponed"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(xpath("/html/body/div/div/main/h1/text()")
                        .string("Отложенное"));
    }

    @Test
    void addRatingValue() throws Exception {
        mockMvc.perform(post("/book/changeBookStatus/rating/ksturley1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new JSONObject().put("bookId", "ksturley1").put("value", "5").toString()))
                .andExpect(status().is3xxRedirection());
    }

    @Test
    void handleChangeBookStatus() throws Exception {
        mockMvc.perform(post("/book/changeBookStatus/ksturley1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new JSONObject().put("booksIds", "ksturley1").put("status", "CART").toString()))
                .andExpect(status().is3xxRedirection())
                .andExpect(cookie().value("cartContents", "ksturley1"));
    }
}