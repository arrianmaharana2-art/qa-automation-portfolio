package com.qa.portfolio.utils;

import com.qa.portfolio.base.BaseTest;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.ITestListener;
import org.testng.ITestResult;

/**
 * Saves a screenshot to target/screenshots whenever a test fails, so the
 * failure can be attached to a bug report.
 */
public class ScreenshotListener implements ITestListener {

    @Override
    public void onTestFailure(ITestResult result) {
        Object testInstance = result.getInstance();
        if (!(testInstance instanceof BaseTest)) {
            return;
        }
        WebDriver driver = ((BaseTest) testInstance).getDriver();
        if (!(driver instanceof TakesScreenshot)) {
            return;
        }
        try {
            File source = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            Path folder = Paths.get("target", "screenshots");
            Files.createDirectories(folder);
            String time = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));
            Path target = folder.resolve(result.getMethod().getMethodName() + "_" + time + ".png");
            Files.copy(source.toPath(), target, StandardCopyOption.REPLACE_EXISTING);
            System.out.println("Screenshot saved: " + target.toAbsolutePath());
        } catch (Exception e) {
            System.err.println("Could not capture screenshot: " + e.getMessage());
        }
    }
}
