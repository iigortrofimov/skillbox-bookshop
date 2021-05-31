package com.bookshop.mybookshop.security;

import com.bookshop.mybookshop.dao.UserDataEditionRepository;
import com.bookshop.mybookshop.domain.user.UserDataEdition;
import com.bookshop.mybookshop.dto.ChangeUserDataForm;
import com.bookshop.mybookshop.security.jwt.JWTUtil;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

@Service
@RequiredArgsConstructor
public class BookStoreUserRegister {

    private final BookStoreUserRepository bookStoreUserRepository;
    private final UserDataEditionRepository userDataEditionRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final BookStoreUserDetailsService bookStoreUserDetailsService;
    private final JWTUtil jwtUtil;

    public BookStoreUser registerNewUser(RegistrationForm registrationForm) {

        BookStoreUser userByEmail = bookStoreUserRepository.findByEmail(registrationForm.getEmail());
        BookStoreUser userByPhone = bookStoreUserRepository.findByPhone(registrationForm.getPhone());

        if (userByEmail == null && userByPhone == null) {
            BookStoreUser newUser = new BookStoreUser();
            newUser.setEmail(registrationForm.getEmail());
            newUser.setName(registrationForm.getName());
            newUser.setPassword(passwordEncoder.encode(registrationForm.getPassword()));
            newUser.setPhone(registrationForm.getPhone());
            newUser.setProvider(Provider.LOCAL);
            newUser.setBalance(0.00);
            return bookStoreUserRepository.save(newUser);
        } else {
            return userByPhone;
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

    public ContactConfirmationResponse jwtLoginByPhoneNumber(ContactConfirmationPayload payload) {
        RegistrationForm registrationForm = new RegistrationForm();
        registrationForm.setPhone(payload.getContact());
        registrationForm.setPassword(payload.getCode());
        registerNewUser(registrationForm);
        UserDetails userDetails = bookStoreUserDetailsService.loadUserByUsername(payload.getContact());
        String jwtToken = jwtUtil.generateToken(userDetails);
        ContactConfirmationResponse response = new ContactConfirmationResponse();
        response.setResult(jwtToken);
        return response;
    }

    public BookStoreUser getCurrentUser() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof BookStoreUserDetails) {
            String username = ((BookStoreUserDetails) principal).getUsername();
            if (username.contains("@")) {
                return bookStoreUserRepository.findByEmail(username);
            }
        } else if (principal instanceof CustomOAuth2User) {
            return bookStoreUserRepository.findByEmail(((CustomOAuth2User) principal).getEmail());
        }
        BookStoreUserDetails bookStoreUserDetails = (BookStoreUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return bookStoreUserDetails.getBookStoreUser();
    }

    public boolean changePassword(ChangePasswordForm changePasswordForm, Model model) {
        BookStoreUser userByEmail = bookStoreUserRepository.findByEmail(changePasswordForm.getEmail());
        BookStoreUser userByPhone = bookStoreUserRepository.findByPhone(changePasswordForm.getPhone());
        if (userByEmail.equals(userByPhone) && passwordEncoder.matches(changePasswordForm.getOldPassword(), userByEmail.getPassword())) {
            userByEmail.setPassword(passwordEncoder.encode(changePasswordForm.getNewPassword()));
            bookStoreUserRepository.save(userByEmail);
            return true;
        } else {
            model.addAttribute("changePassError", true);
            return false;
        }
    }

    public void saveTempUserDataChanges(ChangeUserDataForm changeUserDataForm, String changeUuid, String userEmail) {
        UserDataEdition userDataEdition = new UserDataEdition(changeUserDataForm, changeUuid, userEmail);
        userDataEditionRepository.save(userDataEdition);
    }

    public boolean applyUserDataChanges(String uuid) {
        UserDataEdition userDataEditionFromDB = userDataEditionRepository.findById(UUID.fromString(uuid)).orElse(null);
        if (userDataEditionFromDB != null) {
            BookStoreUser user = bookStoreUserRepository.findByEmail(userDataEditionFromDB.getUserDetailEmail());
            if (userDataEditionFromDB.getName() != null) {
                user.setName(userDataEditionFromDB.getName());
            }
            if (userDataEditionFromDB.getMail() != null) {
                user.setEmail(userDataEditionFromDB.getMail());
            }
            if (userDataEditionFromDB.getPhone() != null) {
                user.setPhone(userDataEditionFromDB.getPhone());
            }
            if (userDataEditionFromDB.getPassword() != null) {
                user.setPassword(passwordEncoder.encode(userDataEditionFromDB.getPassword()));
            }
            bookStoreUserRepository.save(user);
            return true;
        }
        return false;
    }
}
