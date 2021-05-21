package com.bookshop.mybookshop.services.impl;

import com.bookshop.mybookshop.dao.GenreRepository;
import com.bookshop.mybookshop.domain.book.Genre;
import com.bookshop.mybookshop.dto.GenreDto;
import com.bookshop.mybookshop.services.GenreService;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class GenreServiceImpl implements GenreService {

    private final GenreRepository genreRepository;

    public List<GenreDto> receiveAllGenresDtoSortedList() {
        //итоговый список на отдачу
        List<GenreDto> finalGenreDtos = new ArrayList<>(); //size = 0
        //список первого уровня
        List<Genre> firstLevelGenre = genreRepository.findByParentIdIsNull();
        if (firstLevelGenre.isEmpty()) {
            return finalGenreDtos;
        }
        firstLevelGenre.forEach(firstLevGenre -> finalGenreDtos.add(recursiveGenreMapper(firstLevGenre)));
        finalGenreDtos.sort(Comparator.comparingInt(GenreDto::getCount).reversed());
        return finalGenreDtos;
    }

    private GenreDto recursiveGenreMapper(Genre genre) {
        GenreDto genreDto = new GenreDto();
        genreDto.setName(genre.getName());
        genreDto.setSlug(genre.getSlug());
        //create SubGenre List
        List<Genre> subGenreList = new ArrayList<>(genreRepository.findByParentIdIs(genre.getId()));
        if (subGenreList.isEmpty()) {
            genreDto.setCount(genre.getCount());
            return genreDto;
        }
        List<GenreDto> genreDtos = new ArrayList<>();
        subGenreList.forEach(subGenre -> genreDtos.add(recursiveGenreMapper(subGenre)));
        genreDtos.sort(Comparator.comparingInt(GenreDto::getCount).reversed());
        genreDto.setSubGenres(genreDtos);
        //count
        Integer count = genreDtos.stream()
                .map(GenreDto::getCount)
                .map(Optional::ofNullable)
                .reduce(sum)
                .flatMap(Function.identity())
                .orElse(0);
        genreDto.setCount(count + genre.getCount());
        return genreDto;
    }

    private static final BinaryOperator<Optional<Integer>> sum = (a, b) -> a.isPresent() ?
            (b.isPresent() ? a.map(n -> b.get() + n) : a) : b;
}
