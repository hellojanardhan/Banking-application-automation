package com.testcase5;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import io.github.bonigarcia.wdm.WebDriverManager;

public class Screenshot 
{
	public WebDriver driver;
	public static String homePageTitle="Guru99 Bank Manager HomePage";
	public static final String EXPECT_ERROR = "User or Password is not valid";
	public static String actualError;
	public static String actualTitle;
	@BeforeTest
	public void setUp() {
		WebDriverManager.firefoxdriver().setup();
		driver=new FirefoxDriver();
	}
	@Test(dataProvider = "loginData")
	public void login(String uid,String password) throws InterruptedException, IOException 
	{
		driver.get("http://www.demo.guru99.com/V4/");
		driver.findElement(By.name("uid")).clear();
		Thread.sleep(1000);
		driver.findElement(By.name("uid")).sendKeys(uid);
		Thread.sleep(1000);
		driver.findElement(By.name("password")).clear();
		Thread.sleep(1000);
		driver.findElement(By.name("password")).sendKeys(password);

		driver.findElement(By.name("btnLogin")).click();

			Thread.sleep(3000);
			String verifymanagerId="Manger Id : "+uid;
			String alertMsg;
			String acutalAlertMsg="User or Password is not valid";
			String acutalId=driver.findElement(By.cssSelector("tr.heading3 > td:nth-child(1)")).getText();
			
			
			if (driver.getTitle().contains(homePageTitle)) {
					System.out.println(verifymanagerId);
			}
			else {
				try {
					Alert alert=driver.switchTo().alert();
					alertMsg=alert.getText();
					alert.accept();
					System.out.println(alertMsg);
					if(acutalAlertMsg.contains(alertMsg)) {
						TakesScreenshot scrShot =((TakesScreenshot)driver);
						File SrcFile=scrShot.getScreenshotAs(OutputType.FILE);
						File DestFile=new File("../AutomationBankingApplication/screenShots/img2.jpg");
						FileUtils.copyFile(SrcFile, DestFile);
					}
				} catch (NoAlertPresentException e) {
					// TODO: handle exception
					System.out.println("user with"+uid+"failed the test");
				}
			
			}

			
	}


	@DataProvider(name = "loginData")
	public String[][] loginCredentials() throws InvalidFormatException, IOException{
		File file=new File("../AutomationBankingApplication/dataFiles/loginTests.xlsx");
		FileInputStream inputStream=new FileInputStream(file);
		XSSFWorkbook workbook=new XSSFWorkbook(file);

		XSSFSheet sheet=workbook.getSheetAt(0);

		int rowCount=sheet.getLastRowNum();
		int colCount=sheet.getRow(0).getLastCellNum();

		System.out.println(rowCount);
		System.out.println(colCount);

		String[][] data=new String[rowCount][colCount];
		for(int i=0;i<rowCount;i++) {
			for(int j=0;j<colCount;j++) {
				DataFormatter dataFormat=new DataFormatter();
				data[i][j]=dataFormat.formatCellValue(sheet.getRow(i+1).getCell(j));
			}
		}
		inputStream.close();
		workbook.close();
		return data;
	}
	@AfterTest
	public void closeBrowser() {
		driver.quit();
	}
}
