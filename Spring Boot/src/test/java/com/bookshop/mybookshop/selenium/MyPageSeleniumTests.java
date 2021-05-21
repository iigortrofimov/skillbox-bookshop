package com.bookshop.mybookshop.selenium;

import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

public class MyPageSeleniumTests extends AbstractSeleniumTest {

    @Test
    public void testMyPageAccess() throws InterruptedException {
        MyPage myPage = new MyPage(driver);
        myPage
                .callMyPageAndRedirectToUnauthorizedPage()
                .pause()
                .getSingInPage()
                .pause()
                .changeToEmailMode()
                .pause()
                .setUpEmail("test@mail.com")
                .nextButton()
                .pause()
                .setUpPassword("12345678")
                .pause()
                .comeInButton()
                .pause();

        assertTrue(driver.getPageSource().contains("test@mail.com") &&
                driver.getPageSource().contains("Кондратенко Александр Петрович"));
    }
}
