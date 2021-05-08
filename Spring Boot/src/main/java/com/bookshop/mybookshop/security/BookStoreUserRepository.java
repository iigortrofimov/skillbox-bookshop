package com.bookshop.mybookshop.security;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookStoreUserRepository extends JpaRepository<BookStoreUser, Integer> {
    BookStoreUser findByEmail(String email);
}
