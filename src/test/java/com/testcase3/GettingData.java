package com.testcase3;

import static org.testng.Assert.assertEquals;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.AfterTest;
import org.testng.annotations.Test;

import io.github.bonigarcia.wdm.WebDriverManager;

public class GettingData {
	static String username;
	static String password;
	public WebDriver driver; 
	
	public static String homePageTitle="Guru99 Bank Manager HomePage";
	
	@Test
	public void Test() throws IOException, InterruptedException {
		WebDriverManager.firefoxdriver().setup();
		driver= new FirefoxDriver();
		
		FileInputStream fis = new FileInputStream("../AutomationBankingApplication/dataFiles/LoginTests.xlsx");
        Workbook workbook = new XSSFWorkbook(fis);
        Sheet sheet = workbook.getSheet("Sheet1");

        int numRows = sheet.getLastRowNum() + 1;
        int numCols = sheet.getRow(0).getLastCellNum();
        
        String[][] testData = new String[numRows][numCols];
        
        
        for (int i = 1; i <numRows; i++) {
            for (int j = 0; j < numCols; j++) {
                testData[i][j] = sheet.getRow(i).getCell(j).getStringCellValue();
            }
        }
        
        for (int i = 1; i < numRows; i++) {
            username= testData[i][0];
            password= testData[i][1];

            // Perform test actions using WebDriver
            driver.get("http://www.demo.guru99.com/V4/");

            // Locate elements and perform actions (replace with your specific logic)
            driver.findElement(By.name("uid")).sendKeys(username);
            driver.findElement(By.name("password")).sendKeys(password);
            driver.findElement(By.name("btnLogin")).click();

            assertEquals(driver.getTitle(), homePageTitle);
            
            Thread.sleep(3000);
            
            //taking screenshot
            TakesScreenshot scrShot =((TakesScreenshot)driver);
            File SrcFile=scrShot.getScreenshotAs(OutputType.FILE);
            File DestFile=new File("../AutomationBankingApplication/screenShots/img.jpg");
            FileUtils.copyFile(SrcFile, DestFile);
        }
        workbook.close();
	}
	@AfterTest
	public void closeBrowser() {
		driver.quit();
	}
}
