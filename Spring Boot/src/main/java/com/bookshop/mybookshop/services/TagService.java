package com.bookshop.mybookshop.services;

import com.bookshop.mybookshop.dao.TagRepository;
import com.bookshop.mybookshop.domain.BookTag;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class TagService {

    private final TagRepository tagRepository;

    public List<BookTag> receiveAllBookTags() {
        return tagRepository.findAll();
    }

    public List<BookTag> receiveBookTagsWithXsTag() {
        return createBookTagListFromCriterias(0, 2);
    }

    public List<BookTag> receiveBookTagsWithSmTag() {
        return createBookTagListFromCriterias(3, 5);
    }

    public List<BookTag> receiveBookTagsWithTag() {
        return createBookTagListFromCriterias(6, 10);
    }

    public List<BookTag> receiveBookTagsWithMdTag() {
        return createBookTagListFromCriterias(11, 15);
    }

    public List<BookTag> receiveBookTagsWithLgTag() {
        return createBookTagListFromCriterias(16, 9999);
    }

    private List<BookTag> createBookTagListFromCriterias(Integer minCriteria, Integer maxCriteria) {
        return bookTagMap()
                .entrySet()
                .stream()
                .filter(entry -> entry.getKey() >= minCriteria && entry.getKey() <= maxCriteria)
                .map(Map.Entry::getValue)
                .flatMap(Collection::stream)
                .collect(Collectors.toList());
    }

    private Map<Integer, List<BookTag>> bookTagMap() {
        return tagRepository.findAll().stream().collect(Collectors.groupingBy(BookTag::getCount));
    }

}
