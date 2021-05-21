package com.bookshop.mybookshop.services.impl;

import com.bookshop.mybookshop.dao.TagRepository;
import com.bookshop.mybookshop.domain.book.BookTag;
import com.bookshop.mybookshop.services.TagService;
import java.util.Arrays;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@SpringBootTest
class TagServiceImplTest {

    @MockBean
    private TagRepository tagRepository;

    private final TagService tagService;

    @Autowired
    TagServiceImplTest(TagService tagService) {
        this.tagService = tagService;
    }

    @Test
    void receiveBookTagsWithXsTag() {
        BookTag bookTag1 = new BookTag();
        bookTag1.setSlug("tag1");
        bookTag1.setCount(1);

        BookTag bookTag2 = new BookTag();
        bookTag2.setSlug("tag2");
        bookTag2.setCount(2);

        BookTag bookTag3 = new BookTag();
        bookTag3.setSlug("tag3");
        bookTag3.setCount(3);

        Mockito.doReturn(Arrays.asList(bookTag1, bookTag2, bookTag3)).when(tagRepository).findAll();

        List<BookTag> bookTagsFromDB = tagService.receiveBookTagsWithXsTag();
        assertEquals(2, bookTagsFromDB.size());
        assertTrue(bookTagsFromDB.contains(bookTag1) && bookTagsFromDB.contains(bookTag2));
        assertFalse(bookTagsFromDB.contains(bookTag3));
    }

    @Test
    void receiveAllBookTags() {
        BookTag bookTag1 = new BookTag();
        bookTag1.setSlug("tag1");

        BookTag bookTag2 = new BookTag();
        bookTag2.setSlug("tag2");

        Mockito.doReturn(Arrays.asList(bookTag1, bookTag2)).when(tagRepository).findAll();
        List<BookTag> bookTagsFromDB = tagService.receiveAllBookTags();
        assertNotNull(bookTagsFromDB);
        assertFalse(bookTagsFromDB.isEmpty());
        assertTrue(bookTagsFromDB.contains(bookTag1) && bookTagsFromDB.contains(bookTag2));
    }
}