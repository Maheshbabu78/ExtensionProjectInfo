package com.example.ExtensionProject;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertSame;

import java.io.File;
import java.io.IOException;
import java.lang.management.ManagementFactory;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.CodeSource;

import org.apache.commons.exec.util.StringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;

import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import org.testng.asserts.Assertion;
import org.testng.internal.BaseTestMethod;

import com.agent.JavaAgent;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.reporter.ExtentXReporter;
import com.google.common.base.Verify;
import com.sun.tools.attach.AttachNotSupportedException;
import com.sun.tools.attach.VirtualMachine;

public class WebApplicationTesting {

	static ExtentTest test;
	static ExtentReports report;

	@Test
	public void sampleTestCase() {
		assertEquals(1, 1);
	}

	@BeforeClass
	public void loadAgent() throws Exception {

	}

	@Test
	public void loginTestCase() throws Exception {		
		Thread.sleep(1000);
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
		String u1 = d.getCurrentUrl();
		String expected = "http://localhost:4200/admin/dashboard";
		String methodName = new Object() {
		}.getClass().getEnclosingMethod().getName();
		ExtentHtmlReporter html = new ExtentHtmlReporter("src/main/resources/templates/index.html");
		ExtentReports extent = new ExtentReports();
		extent.attachReporter(html);
		ExtentTest logger = extent.createTest(methodName, "login test case");
		logger.log(Status.INFO, "login in e banking");
		if (u1.equals(expected)) {
			logger.log(Status.PASS, "login Sucess");
		} else {
			logger.log(Status.FAIL, "login failed");
		}
		extent.flush();
		assertEquals(u1, expected);
		
		d.close();
		
	}

}
