package com.bookshop.mybookshop.selenium;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

public class MyPage {
    private String url = "http://localhost:8082/my";

    private ChromeDriver driver;

    public MyPage(ChromeDriver driver) {
        this.driver = driver;
    }

    public MyPage callMyPageAndRedirectToUnauthorizedPage() {
        driver.get(url);
        return this;
    }

    public MyPage pause() throws InterruptedException {
        Thread.sleep(2000);
        return this;
    }

    public MyPage getSingInPage() {
        WebElement element = driver.findElement(By.id("login"));
        element.click();
        return this;
    }

    public MyPage changeToEmailMode() {
        WebElement element = driver.findElement(By.id("emailButton"));
        element.click();
        return this;
    }

    public MyPage setUpEmail(String email) {
        WebElement element = driver.findElement(By.id("mail"));
        element.sendKeys(email);
        return this;
    }

    public MyPage nextButton() {
        WebElement element = driver.findElement(By.id("sendauth"));
        element.click();
        return this;
    }

    public MyPage setUpPassword(String password) {
        WebElement element = driver.findElement(By.id("mailcode"));
        element.sendKeys(password);
        return this;
    }

    public MyPage comeInButton() {
        WebElement element = driver.findElement(By.id("toComeInMail"));
        element.click();
        return this;
    }
}
