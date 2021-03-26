package com.bookshop.mybookshop.services;

import com.bookshop.mybookshop.dto.GenreDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface GenreService {

    List<GenreDto> receiveAllGenresDtoSortedList();
}
