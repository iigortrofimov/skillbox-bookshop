package com.bookshop.mybookshop.dao;

import com.bookshop.mybookshop.domain.author.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthorRepository extends JpaRepository<Author, Integer> {

    Author findByLastNameAndFirstName(String lastName, String firstName);

}
