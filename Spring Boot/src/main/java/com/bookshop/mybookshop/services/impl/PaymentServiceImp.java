package com.bookshop.mybookshop.services.impl;

import com.bookshop.mybookshop.dao.BalanceTransactionRepository;
import com.bookshop.mybookshop.domain.book.Book;
import com.bookshop.mybookshop.domain.user.BalanceTransaction;
import com.bookshop.mybookshop.dto.PaymentResponse;
import com.bookshop.mybookshop.security.BookStoreUser;
import com.bookshop.mybookshop.security.BookStoreUserDetails;
import com.bookshop.mybookshop.security.BookStoreUserRepository;
import com.bookshop.mybookshop.security.CustomOAuth2User;
import com.bookshop.mybookshop.services.PaymentService;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.Principal;
import java.time.LocalDateTime;
import java.util.List;
import javax.xml.bind.DatatypeConverter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PaymentServiceImp implements PaymentService {

    @Value("${robokassa.merchant.login}")
    private String merchantLogin;

    @Value("${robokassa.pass.first.test}")
    private String firstTestPass;

    private final BookStoreUserRepository bookStoreUserRepository;

    private final BalanceTransactionRepository balanceTransactionRepository;

    @Override
    public String getPaymentUrlForBooksFromCart(List<Book> booksFromCookieSlugs) throws NoSuchAlgorithmException {
        Double paymentSumTotal = booksFromCookieSlugs.stream().mapToDouble(Book::discountedPrice).sum();
        return getPaymentUrl(paymentSumTotal);
    }

    @Override
    @Transactional
    public String topUpUserAccount(Double sum, Principal principal) throws NoSuchAlgorithmException {
        BookStoreUser user;
        if (principal instanceof OAuth2AuthenticationToken) {
            user = bookStoreUserRepository.findByEmail(((CustomOAuth2User) ((OAuth2AuthenticationToken) principal)
                    .getPrincipal()).getEmail());
        } else {
            user = bookStoreUserRepository.findByEmail(principal.getName());
        }
        user.setBalance(user.getBalance() + sum);
        bookStoreUserRepository.save(user);

        createTransaction(user, sum, "Пополнение счета в размере: " + sum);

        return getPaymentUrl(sum);
    }

    private String getPaymentUrl(Double sum) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("MD5");
        String invId = "5"; //just for Testing
        md.update((merchantLogin + ":" + sum.toString() + ":" + invId + ":" + firstTestPass).getBytes());
        return "https://auth.robokassa.ru/Merchant/Index.aspx" +
                "?MerchantLogin=" + merchantLogin +
                "&InvId=" + invId +
                "&Culture=ru" +
                "&Encoding=utf-8" +
                "&OutSum=" + sum.toString() +
                "&SignatureValue=" + DatatypeConverter.printHexBinary(md.digest()).toUpperCase() +
                "&IsTest=1";
    }

    public PaymentResponse payFromUserAccount(List<Book> booksFromCookieSlugs) {

        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        BookStoreUser user;

        if (principal instanceof BookStoreUserDetails && ((BookStoreUserDetails) principal).getUsername().contains("@")) {
            user = bookStoreUserRepository.findByEmail(((BookStoreUserDetails) principal).getUsername());
        } else if (principal instanceof CustomOAuth2User) {
            user = bookStoreUserRepository.findByEmail(((CustomOAuth2User) principal).getEmail());
        } else {
            return new PaymentResponse(false, "Пожалуйста авторизируйтесь!");
        }
        Double paymentSumTotal = booksFromCookieSlugs.stream().mapToDouble(Book::discountedPrice).sum();
        if (user.getBalance() < paymentSumTotal) {
            return new PaymentResponse(false, "Недостаточно денежных средств");
        }

        createTransaction(user, paymentSumTotal, "Списание средств в размере: " + paymentSumTotal);

        user.setBalance(user.getBalance() - paymentSumTotal);
        bookStoreUserRepository.save(user);
        return new PaymentResponse(true, "Успешное списание денежных средств");
    }

    private void createTransaction(BookStoreUser user, Double value, String message) {
        BalanceTransaction transaction = new BalanceTransaction();
        transaction.setUser(user);
        transaction.setDateTime(LocalDateTime.now());
        transaction.setDescription(message);
        transaction.setValue(value);
        balanceTransactionRepository.save(transaction);
    }
}
