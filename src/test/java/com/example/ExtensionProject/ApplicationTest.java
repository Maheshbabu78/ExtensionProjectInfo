package com.example.ExtensionProject;

import static org.testng.Assert.assertEquals;

import java.io.File;
import java.net.MalformedURLException;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;

public class ApplicationTest {

	@Test
	public void withdrawRequestTest() throws InterruptedException, MalformedURLException {
		System.setProperty("webdriver.chrome.driver",
				"C:\\Users\\NMAHESHBABU\\chromedriver\\chromedriver_win32\\chromedriver.exe");

		ChromeOptions options = new ChromeOptions();
		options.addExtensions(new File("C:\\Users\\NMAHESHBABU\\testChromeExtension.crx"));
		WebDriver d = new ChromeDriver(options);
		d.get("http://localhost:4200/login");
		d.findElement(By.id("email_id")).sendKeys(" nadigotimahesh@gmail.com");
		d.findElement(By.id("password_1")).sendKeys("Mahesh@11");
		d.findElement(By
				.xpath("/html/body/app-root/div/div/app-login/div/div/div/div/div/div/div/div/div/form/div[3]/button"))
				.click();
		Thread.sleep(2000);
		d.findElement(By.xpath("/html/body/app-root/div/nav/div[2]/ul/li[6]/a/span")).click();
		String u1 = d.getCurrentUrl();
		String expected = "http://localhost:4200/admin/withdraw/request";
		String methodName = new Object() {
		}.getClass().getEnclosingMethod().getName();
		ExtentHtmlReporter html = new ExtentHtmlReporter("src/main/resources/templates/index.html");
		ExtentReports extent = new ExtentReports();
		extent.attachReporter(html);
		ExtentTest logger = extent.createTest(methodName, "Withdraw request test case");
		logger.log(Status.INFO, "withdraw request e banking");
		if (u1.equals(expected)) {
			logger.log(Status.PASS, "Withdraw request created");
		} else {
			logger.log(Status.FAIL, "request failed");
		}
		extent.flush();
		assertEquals(u1, expected);
		d.close();

	}

}
