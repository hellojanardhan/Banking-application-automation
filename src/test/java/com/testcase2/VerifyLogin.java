package com.testcase2;

import static org.testng.Assert.assertEquals;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import io.github.bonigarcia.wdm.WebDriverManager;


public class VerifyLogin extends utilitis{
	@BeforeTest
	public void driverSetUp() {
		WebDriverManager.firefoxdriver().setup();
		driver=new FirefoxDriver();
		pClass=new pageObjectClass(driver);
	}
	@Test
	public void login() {
		driver.get(url);
		pClass.setUserID(userID);
		pClass.setPassword(password);
		pClass.clickLogin();
	}
	@Test
	public void verifyLogin() throws IOException, InterruptedException {
		assertEquals(driver.getTitle(), homePageTitle);
		Thread.sleep(2000);
		File SrcFile=((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
		File DestFile=new File("../AutomationBankingApplication/screenShots/im1.jpg");
		FileUtils.copyFile(SrcFile, DestFile);
	}
	@AfterTest
	public void closeBrowser() {
		driver.quit();
	}
}
