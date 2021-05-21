package com.bookshop.mybookshop.selenium;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

public class BookPage {

    private String sighInPageUrl = "http://localhost:8082/signin";
    private String bookPageUrl = "http://localhost:8082/book/kbuckerfieldkd";

    private ChromeDriver driver;

    public BookPage(ChromeDriver driver) {
        this.driver = driver;
    }

    public BookPage callSignInPage() {
        driver.get(sighInPageUrl);
        return this;
    }

    public BookPage pause() throws InterruptedException {
        Thread.sleep(2000);
        return this;
    }

    public BookPage changeToEmailMode() {
        WebElement element = driver.findElement(By.id("emailButton"));
        element.click();
        return this;
    }

    public BookPage setUpEmail(String email) {
        WebElement element = driver.findElement(By.id("mail"));
        element.sendKeys(email);
        return this;
    }

    public BookPage nextButton() {
        WebElement element = driver.findElement(By.id("sendauth"));
        element.click();
        return this;
    }

    public BookPage setUpPassword(String password) {
        WebElement element = driver.findElement(By.id("mailcode"));
        element.sendKeys(password);
        return this;
    }

    public BookPage comeInButton() {
        WebElement element = driver.findElement(By.id("toComeInMail"));
        element.click();
        return this;
    }

    public BookPage callSpecificBookPage() {
        driver.get(bookPageUrl);
        return this;
    }

    public BookPage addReviewComment(String comment) {
        WebElement element = driver.findElement(By.id("commentarea"));
        element.sendKeys(comment);
        return this;
    }

    public BookPage submit() {
        WebElement element = driver.findElement(By.id("submitcomment"));
        element.submit();
        return this;
    }

    public BookPage reloadPage() {
        return callSpecificBookPage();
    }

    public BookPage addLikeToReview() {
        WebElement element = driver.findElement(By.id("likebutton"));
        element.click();
        return this;
    }
}
