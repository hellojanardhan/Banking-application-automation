package com.testcase2;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class pageObjectClass {
	public WebDriver driver;
	public pageObjectClass(WebDriver d) {
		driver=d;
		PageFactory.initElements(d, this);
	}
	
	@FindBy(name = "uid")
	WebElement userId;
	
	@FindBy(name = "password")
	WebElement Password;
	
	@FindBy(name = "btnLogin")
	WebElement login;
	
	public void setUserID(String uid) {
		userId.sendKeys(uid);
	}
	
	public void setPassword(String password) {
		Password.sendKeys(password);
	}
	
	public void clickLogin() {
		login.click();
	}
}
