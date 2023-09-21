package com.testcase1;

import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import io.github.bonigarcia.wdm.WebDriverManager;

public class Login extends utilitis{
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
	
	@AfterTest
	public void closeBrowser() {
		driver.quit();
	}
	
}
