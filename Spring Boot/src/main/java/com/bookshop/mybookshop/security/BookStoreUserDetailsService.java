package com.bookshop.mybookshop.security;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BookStoreUserDetailsService implements UserDetailsService {

    private final BookStoreUserRepository bookStoreUserRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        BookStoreUser bookStoreUser = bookStoreUserRepository.findByEmail(email);
        if (bookStoreUser != null) {
            return new BookStoreUserDetails(bookStoreUser);
        } else {
            throw new UsernameNotFoundException("User not found!");
        }
    }
}
