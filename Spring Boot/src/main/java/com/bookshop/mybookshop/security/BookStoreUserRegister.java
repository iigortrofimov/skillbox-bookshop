package com.bookshop.mybookshop.security;

import com.bookshop.mybookshop.security.jwt.JWTUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BookStoreUserRegister {

    private final BookStoreUserRepository bookStoreUserRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final BookStoreUserDetailsService bookStoreUserDetailsService;
    private final JWTUtil jwtUtil;

    public void registerNewUser(RegistrationForm registrationForm) {
        if (bookStoreUserRepository.findByEmail(registrationForm.getEmail()) == null) {
            BookStoreUser newUser = new BookStoreUser();
            newUser.setEmail(registrationForm.getEmail());
            newUser.setName(registrationForm.getName());
            newUser.setPassword(passwordEncoder.encode(registrationForm.getPassword()));
            newUser.setPhone(registrationForm.getPhone());
            newUser.setProvider(Provider.LOCAL);
            bookStoreUserRepository.save(newUser);
        }
    }

    public ContactConfirmationResponse login(ContactConfirmationPayload payload) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(payload.getContact(),
                payload.getCode()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        ContactConfirmationResponse response = new ContactConfirmationResponse();
        response.setResult("true");
        return response;
    }

    public ContactConfirmationResponse jwtLogin(ContactConfirmationPayload payload) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(payload.getContact(),
                payload.getCode()));
        BookStoreUserDetails userDetails = (BookStoreUserDetails) bookStoreUserDetailsService.loadUserByUsername(payload.getContact());
        String jwtToken = jwtUtil.generateToken(userDetails);
        ContactConfirmationResponse response = new ContactConfirmationResponse();
        response.setResult(jwtToken);
        return response;
    }

    public BookStoreUser getCurrentUser() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof BookStoreUserDetails) {
            return ((BookStoreUserDetails) principal).getBookStoreUser();
        } else if (principal instanceof CustomOAuth2User) {
            BookStoreUser bookStoreUser = new BookStoreUser();
            bookStoreUser.setName(((CustomOAuth2User) principal).getName());
            bookStoreUser.setEmail(((CustomOAuth2User) principal).getEmail());
            return bookStoreUser;
        }
        BookStoreUserDetails bookStoreUserDetails = (BookStoreUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return bookStoreUserDetails.getBookStoreUser();
    }
}
