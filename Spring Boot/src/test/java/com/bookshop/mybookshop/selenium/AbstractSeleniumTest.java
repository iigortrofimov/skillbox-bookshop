package com.bookshop.mybookshop.selenium;

import java.util.concurrent.TimeUnit;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public abstract class AbstractSeleniumTest {

    ChromeDriver driver;

    @BeforeEach
    void setUp() {
        System.setProperty("webdriver.chrome.driver",
                "C:\\Users\\itrofimo\\Desktop\\demoprojects\\Skillbox\\util\\chromedriver_win32\\chromedriver.exe");
        driver = new ChromeDriver();
        driver.manage().timeouts().pageLoadTimeout(7, TimeUnit.MINUTES);
    }

    @AfterEach
    void tearDown() {
        driver.quit();
    }
}
