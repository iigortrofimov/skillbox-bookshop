package com.bookshop.mybookshop.security;

import com.bookshop.mybookshop.aspect.logging.annotations.JWTUserDetailsTraceable;
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
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        BookStoreUser bookStoreUser = bookStoreUserRepository.findByEmail(login);
        if (bookStoreUser != null) {
            return new BookStoreUserDetails(bookStoreUser);
        }
        bookStoreUser = bookStoreUserRepository.findByPhone(login);
        if (bookStoreUser != null) {
            return new PhoneNumberUserDetails(bookStoreUser);
        } else {
            throw new UsernameNotFoundException("User not found!");
        }
    }

    @JWTUserDetailsTraceable
    public UserDetails loadUserByUsernameFromJWT(String login) throws UsernameNotFoundException {
        return loadUserByUsername(login);
    }

    public BookStoreUser processOAuthPostLogin(String email, String name) {
        BookStoreUser existUser = bookStoreUserRepository.findByEmail(email);
        if (existUser == null) {
            BookStoreUser newUser = new BookStoreUser();
            newUser.setEmail(email);
            newUser.setName(name);
            newUser.setProvider(Provider.GOOGLE);
            newUser.setBalance(0.0);
            return bookStoreUserRepository.save(newUser);
        }
        return existUser;
    }
}
