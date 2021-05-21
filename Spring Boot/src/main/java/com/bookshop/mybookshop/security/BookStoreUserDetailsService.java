package com.bookshop.mybookshop.security;

import java.util.Optional;
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
        BookStoreUser bookStoreUser = getAuthenticatedUserByEmail(email);
        return new BookStoreUserDetails(bookStoreUser);
    }

    private BookStoreUser getAuthenticatedUserByEmail(String email) throws UsernameNotFoundException {
        return Optional.ofNullable(bookStoreUserRepository
                .findByEmail(email))
                .orElseThrow(() -> new UsernameNotFoundException("User not found!"));
    }

    public BookStoreUser processOAuthPostLogin(String email, String name) {
        BookStoreUser existUser = bookStoreUserRepository.findByEmail(email);
        if (existUser == null) {
            BookStoreUser newUser = new BookStoreUser();
            newUser.setEmail(email);
            newUser.setName(name);
            newUser.setProvider(Provider.GOOGLE);
            return bookStoreUserRepository.save(newUser);
        }
        return existUser;
    }
}
