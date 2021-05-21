package com.bookshop.mybookshop.selenium;

import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

public class BookReviewSeleniumTest extends AbstractSeleniumTest {

    @Test
    public void testReviewAdditionAndThenLikeIt() throws InterruptedException {
        BookPage bookPage = new BookPage(driver);
        bookPage
                .callSignInPage()
                .pause()
                .changeToEmailMode()
                .pause()
                .setUpEmail("test@mail.com")
                .nextButton()
                .pause()
                .setUpPassword("12345678")
                .pause()
                .comeInButton()
                .pause()
                .callSpecificBookPage()
                .pause()
                .addReviewComment("test comments")
                .pause()
                .submit()
                .pause()
                .reloadPage()
                .pause()
                .addLikeToReview()
                .pause()
                .reloadPage()
                .pause()
                .addLikeToReview()
                .reloadPage()
                .pause();

        assertTrue(driver.getPageSource().contains("Tom"));
        assertTrue(driver.getPageSource().contains("test comments"));
        assertTrue(driver.getPageSource().contains("<span class=\"btn-content\">2</span>"));
    }
}
