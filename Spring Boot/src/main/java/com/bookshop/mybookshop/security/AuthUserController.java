package com.bookshop.mybookshop.security;

import com.bookshop.mybookshop.domain.SmsCode;
import com.bookshop.mybookshop.dto.ChangeUserDataForm;
import com.bookshop.mybookshop.dto.SearchWordDto;
import com.bookshop.mybookshop.services.MessageSenderService;
import java.security.Principal;
import java.util.UUID;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequiredArgsConstructor
public class AuthUserController {

    private final BookStoreUserRegister registrationService;

    private final SmsService smsService;

    private final MessageSenderService messageSenderService;

    @ModelAttribute("searchWordDto")
    public SearchWordDto searchWordDto() {
        return new SearchWordDto();
    }

    @GetMapping("/signin")
    public String handleSignIn() {
        return "signin";
    }

    @GetMapping("/signup")
    public String handleSignUp(Model model) {
        model.addAttribute("regForm", new RegistrationForm());
        return "signup";
    }

    @GetMapping("/changepassword")
    public String handleGetChangePassword(Model model) {
        model.addAttribute("changePassForm", new ChangePasswordForm());
        return "changepassword";
    }

    @PostMapping("/requestContactConfirmation")
    @ResponseBody
    public ContactConfirmationResponse handleRequestContactConfirmation(@RequestBody ContactConfirmationPayload payload) {
        ContactConfirmationResponse response = new ContactConfirmationResponse();
        response.setResult("true");
        if (payload.getContact().contains("@")) {
            return response;
        } else {
            String smsCode = smsService.sendSecretCodeSms(payload.getContact());
            smsService.saveNewCode(new SmsCode(smsCode, 60));
            return response;
        }
    }

    @PostMapping("/requestEmailConfirmation")
    @ResponseBody
    public ContactConfirmationResponse handleRequestEmailConfirmation(@RequestBody ContactConfirmationPayload payload) {
        ContactConfirmationResponse response = new ContactConfirmationResponse();
        messageSenderService.sendCodeViaEmail(payload.getContact(), "BookStore email verification.");
        response.setResult("true");
        return response;
    }

    @PostMapping("/approveContact")
    @ResponseBody
    public ContactConfirmationResponse handleApproveContact(@RequestBody ContactConfirmationPayload payload) {
        ContactConfirmationResponse response = new ContactConfirmationResponse();
        if (smsService.virifyCode(payload.getCode())) {
            response.setResult("true");
        }
        return response;
    }

    @PostMapping("/reg")
    public String handleUserRegistration(RegistrationForm registrationForm, Model model) {
        registrationService.registerNewUser(registrationForm);
        model.addAttribute("regOk", true);
        return "signin";
    }

    @PostMapping("/changepass")
    public String handlePostChangePassword(ChangePasswordForm changePasswordForm, Model model) {
        if (registrationService.changePassword(changePasswordForm, model)) {
            model.addAttribute("changePassOk", true);
        }
        return "signin";
    }

    @PostMapping("/login")
    @ResponseBody
    public ContactConfirmationResponse handleLogin(@RequestBody ContactConfirmationPayload payload,
                                                   HttpServletResponse response) {
        ContactConfirmationResponse loginResponse = registrationService.jwtLogin(payload);
        Cookie cookie = new Cookie("token", loginResponse.getResult());
        response.addCookie(cookie);
        return loginResponse;
    }

    @PostMapping("/login-by-phone-number")
    @ResponseBody
    public ContactConfirmationResponse handleLoginByPhoneNumber(@RequestBody ContactConfirmationPayload payload,
                                                                HttpServletResponse response) {
        if (smsService.virifyCode(payload.getCode())) {
            ContactConfirmationResponse loginResponse = registrationService.jwtLoginByPhoneNumber(payload);
            Cookie cookie = new Cookie("token", loginResponse.getResult());
            response.addCookie(cookie);
            return loginResponse;
        } else {
            return null;
        }
    }

    @GetMapping("/my")
    public String handleMy(Model model) {
        model.addAttribute("curUser", registrationService.getCurrentUser());
        return "my";
    }

    @GetMapping("/profile")
    public String handleProfile(Model model) {
        model.addAttribute("curUser", registrationService.getCurrentUser());
        model.addAttribute("changeUserDataForm", new ChangeUserDataForm());
        return "profile";
    }

    @GetMapping("/403")
    public String handle403() {
        return "403";
    }

    @GetMapping("/401")
    public String handle401() {
        return "401";
    }

    @PostMapping("/changeUserData")
    public String handleChangeUserDataRequest(ChangeUserDataForm changeUserDataForm, RedirectAttributes redirectAttributes,
                                              Principal principal) {
        if (!changeUserDataForm.getPassword().equals(changeUserDataForm.getPasswordReply())) {
            redirectAttributes.addFlashAttribute("changedUserDataMessage", "Пароли не совпадают.");
            return "redirect:/";
        }
        String email = defineUserEmail(principal);
        String changeUuid = UUID.randomUUID().toString();

        registrationService.saveTempUserDataChanges(changeUserDataForm, changeUuid, email);
        messageSenderService.sendMessageViaEmail(email, "Confirm data changing", "Для подтверждения изменений перейдите по ссылке " +
                "http://localhost:8082/confirmchanges/" + changeUuid);

        redirectAttributes.addFlashAttribute("changedUserDataMessage", "Ссылка для подтверждения учетных данных отправлена на ваш email.");
        return "redirect:/";
    }

    @GetMapping("/confirmchanges/{uuid}")
    public String handleConfirmDataChangesPage(@PathVariable String uuid, RedirectAttributes redirectAttributes) {
        if (registrationService.applyUserDataChanges(uuid)) {
            redirectAttributes.addFlashAttribute("changedUserDataMessage", "Учетные данные изменены.");
        } else {
            redirectAttributes.addFlashAttribute("changedUserDataMessage", "Учетная запись не найдена.");
        }
        return "redirect:/";
    }

    private String defineUserEmail(Principal principal) {
        if (principal instanceof OAuth2AuthenticationToken) {
            return ((CustomOAuth2User) ((OAuth2AuthenticationToken) principal).getPrincipal()).getEmail();
        } else {
            return principal.getName();
        }
    }
}
