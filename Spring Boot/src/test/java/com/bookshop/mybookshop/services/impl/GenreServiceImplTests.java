package com.bookshop.mybookshop.services.impl;

import com.bookshop.mybookshop.dao.GenreRepository;
import com.bookshop.mybookshop.domain.book.Genre;
import com.bookshop.mybookshop.dto.GenreDto;
import com.bookshop.mybookshop.services.GenreService;
import java.util.Collections;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@SpringBootTest
class GenreServiceImplTests {

    @MockBean
    private GenreRepository genreRepository;

    private final GenreService genreService;

    @Autowired
    GenreServiceImplTests(GenreService genreService) {
        this.genreService = genreService;
    }

    @Test
    void receiveAllGenresDtoSortedList() {
        Genre genre = new Genre();
        genre.setId(1);
        genre.setName("Genre");
        genre.setSlug("GenreSlug1");
        genre.setCount(1);

        Genre genreLevel2 = new Genre();
        genreLevel2.setId(2);
        genreLevel2.setName("SubGenre");
        genreLevel2.setSlug("GenreSlug2");
        genreLevel2.setParentId(1);
        genreLevel2.setCount(1);

        Mockito.doReturn(Collections.singletonList(genre)).when(genreRepository).findByParentIdIsNull();
        Mockito.doReturn(Collections.singletonList(genreLevel2)).when(genreRepository).findByParentIdIs(1);

        List<GenreDto> genreDtosFromDB = genreService.receiveAllGenresDtoSortedList();
        assertNotNull(genreDtosFromDB);
        assertFalse(genreDtosFromDB.isEmpty());
        assertEquals("Genre", genreDtosFromDB.get(0).getName());
        assertEquals("SubGenre", genreDtosFromDB.get(0).getSubGenres().get(0).getName());
    }
}