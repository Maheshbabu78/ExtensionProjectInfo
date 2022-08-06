package com.example.ExtensionProject;

import static org.testng.Assert.assertEquals;

import java.io.File;
import java.net.MalformedURLException;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;

public class ApplicationTesting {
	
	static ExtentTest test;
	static ExtentReports report;

	@Test
	public void testCaseTwo() {
		assertEquals(2, 2);
	}

	@Test
	public void amountAddedTest() throws InterruptedException, MalformedURLException {
		System.setProperty("webdriver.chrome.driver",
				"C:\\Users\\NMAHESHBABU\\chromedriver\\chromedriver_win32\\chromedriver.exe");		
		ChromeOptions options = new ChromeOptions();
		options.addExtensions(new File("C:\\Users\\NMAHESHBABU\\testChromeExtension.crx"));
		WebDriver d= new ChromeDriver(options);
		d.get("http://localhost:4200/login");
		d.findElement(By.id("email_id")).sendKeys(" nadigotimahesh@gmail.com");
		d.findElement(By.id("password_1")).sendKeys("Mahesh@11");
		d.findElement(By
				.xpath("/html/body/app-root/div/div/app-login/div/div/div/div/div/div/div/div/div/form/div[3]/button"))
				.click();
        Thread.sleep(2000);
		d.findElement(By.xpath("/html/body/app-root/div/nav/div[2]/ul/li[4]/a/span")).click();
		d.findElement(By.id("acc_number")).sendKeys("1990272447469866");
		d.findElement(By.id("acc_amount")).sendKeys("100");
		d.findElement(By.xpath("/html/body/app-root/div/div/app-admin-add-balance/div/div[2]/div/div/div/div/div/form/div[3]/button")).click();
		Thread.sleep(2000);
		String u11= d.getCurrentUrl();
		String name = new Object() {}.getClass().getEnclosingMethod().getName();
		ExtentHtmlReporter html = new ExtentHtmlReporter("src/main/resources/templates/index.html");
		ExtentReports extent = new ExtentReports();
		extent.attachReporter(html);
		ExtentTest logger = extent.createTest(name, "logout test case");
		logger.log(Status.INFO, "logout from e banking");
		String expected="http://localhost:4200/admin/balance/add";
		if(u11.equals(expected)) {
			logger.log(Status.PASS, "Amount added Sucessfully");
		}else {
			logger.log(Status.FAIL, "failed");
		}
		extent.flush();
		assertEquals(u11, expected);
		d.close();

	}
}
