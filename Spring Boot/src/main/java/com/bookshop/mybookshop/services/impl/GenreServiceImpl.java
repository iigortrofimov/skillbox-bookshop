package com.bookshop.mybookshop.services.impl;

import com.bookshop.mybookshop.dao.GenreRepository;
import com.bookshop.mybookshop.domain.book.Genre;
import com.bookshop.mybookshop.dto.GenreDto;
import com.bookshop.mybookshop.services.GenreService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
//TODO убрать закомиченый код
@Service
@AllArgsConstructor
public class GenreServiceImpl implements GenreService {

    private GenreRepository genreRepository;

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
                .reduce(0, Integer::sum);
        genreDto.setCount(count + genre.getCount());
        return genreDto;
    }

}


/*    public List<Genre> getAllGenres(String genreName) {
        List<Genre> allGenres = genreRepository.findBySlugIs(genreName);
        List<Genre> genresByName = genreRepository.findBySlugIs(genreName);
        allGenres.addAll(genresByName);
        genresByName.stream()
                .filter(genre -> genre.getParentId() != null)
                .map(Genre::getParentId)
                .forEach(parentId -> {
                    List<Genre> byParentIdIs = genreRepository.findByParentIdIs(parentId);
                    allGenres.addAll(byParentIdIs);
                    byParentIdIs.stream()
                            .filter(genre -> genre.getParentId() != null)
                            .map(Genre::getParentId)
                            .forEach(parId -> {
                                List<Genre> byParentId = genreRepository.findByParentIdIs(parentId);
                                allGenres.addAll(byParentId);
                            });
                });
        return allGenres;
    }*/

