package com.test;

import org.testng.annotations.Test;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.remote.MobileCapabilityType;
import org.testng.annotations.BeforeClass;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.CellType;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.AfterClass;

public class TestDW005TC2 {

	public AndroidDriver driver;
	JavascriptExecutor js = (JavascriptExecutor) driver;
	 HSSFWorkbook workbook;
	    HSSFSheet sheet;
	    HSSFCell cell;

	@Test
	public void purchaseProduct() throws IOException {
		driver.findElement(By.xpath("//span[@class='icon']")).click();
		driver.findElement(By.xpath("//a[contains(text(),'Computers')]/following-sibling::span")).click();
		driver.findElement(
				By.xpath("//li[@class='active']//ul[@class='sublist firstLevel']//a[contains(text(),'Desktops')]"))
				.click();
		driver.findElement(By.xpath("(//input[@value='Add to cart'])[1]")).click();
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		// Swipe action
		// Find element by link text and store in variable "Element2"
		WebElement Element2 = driver.findElement(By.linkText("Add to compare list"));
		// This will scroll the page till the element is found
		js.executeScript("arguments[0].scrollIntoView();", Element2);

		// Click Addto Cart Button
		driver.findElement(By.xpath("//input[@class='button-1 add-to-cart-button']")).click();
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		// scroll up to see the shopping cart link
		// Find element by link text and store in variable "Element"
		WebElement Element = driver.findElement(By.linkText("Shopping cart"));
		// This will scroll the page till the element is found
		js.executeScript("arguments[0].scrollIntoView();", Element);
		// Click the shopping Cart link
		driver.findElement(By.xpath("//span[contains(.,'Shopping cart')]")).click();
		// Select a Country from drop down
		Select drodown = new Select(driver.findElement(By.xpath("//select[contains(@id,'CountryId')]")));
		drodown.selectByValue("India");
		// Click the accept checkbox
		driver.findElement(By.xpath("//input[contains(@id,'termsofservice')]")).click();
		// Click Checkout Button.
		driver.findElement(By.xpath("//button[contains(@id,'checkout')]")).click();
		// Fill Billing address page
		Select billingdrodown = new Select(
				driver.findElement(By.xpath("//select[contains(@id,'BillingNewAddress_CountryId')]")));
		billingdrodown.selectByValue("India");
		
		
		// Data Driven Steps
	     File userDetails=new File("C:\\IBMProjects\\UPSKILL PROGRAM\\AppiumAssignment\\TestData.xlsx");
	     FileInputStream finput = new FileInputStream(userDetails);
	     workbook = new HSSFWorkbook(finput);
	     sheet= workbook.getSheetAt(0);
	      
	     for(int i=1; i<=sheet.getLastRowNum(); i++)
	     {
	         // Import data for Address1.
	         cell = sheet.getRow(i).getCell(2);
	         cell.setCellType(CellType.STRING);
	         driver.findElement(By.xpath("//input[contains(@id,'BillingNewAddress_City')]")).sendKeys(cell.getStringCellValue());
	          
	         // Import data for Address2.
	         cell = sheet.getRow(i).getCell(3);
	         cell.setCellType(CellType.STRING);
	         driver.findElement(By.xpath("//input[contains(@id,'BillingNewAddress_Address1')]")).sendKeys(cell.getStringCellValue());
		
	      // Import data for Postalcode
	         cell = sheet.getRow(i).getCell(4);
	         cell.setCellType(CellType.STRING);
	         driver.findElement(By.xpath("//input[contains(@id,'BillingNewAddress_ZipPostalCode')]")).sendKeys(cell.getStringCellValue());
		
	         // Import data for Phonenumber
	         cell = sheet.getRow(i).getCell(4);
	         cell.setCellType(CellType.STRING);
	         driver.findElement(By.xpath("//input[contains(@id,'BillingNewAddress_PhoneNumber')]")).sendKeys(cell.getStringCellValue());
				
		/*driver.findElement(By.xpath("//input[contains(@id,'BillingNewAddress_City')]")).sendKeys("Kolkata");
		driver.findElement(By.xpath("//input[contains(@id,'BillingNewAddress_Address1')]")).sendKeys("Kolkata");
		driver.findElement(By.xpath("//input[contains(@id,'BillingNewAddress_ZipPostalCode')]")).sendKeys("700001");
		driver.findElement(By.xpath("//input[contains(@id,'BillingNewAddress_PhoneNumber')]")).sendKeys("9789098767");*/
		
		// Click Continue Button
		driver.findElement(By.xpath("//input[@onclick='Billing.save()']")).click();
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		// Click Continue Button in Shipping Address
		driver.findElement(By.xpath("//input[contains(@onclick,'Shipping.save()')]")).click();
		// Click Continue Button in Shipping Method page
		driver.findElement(By.xpath("//input[contains(@class,'button-1 shipping-method-next-step-button')]")).click();
		// Click Continue Button in Payment Method page
		driver.findElement(By.xpath("//input[contains(@class,'button-1 payment-method-next-step-button')]")).click();
		// Click Continue Button in Payment Information page
		driver.findElement(By.xpath("//input[contains(@class,'button-1 payment-info-next-step-button')]")).click();
		// Click Continue Button in Confirm Order page
		driver.findElement(By.xpath("//input[contains(@class,'button-1 payment-info-next-step-button')]")).click();
		// Find element by link text and store in variable "Element"
		WebElement Element1 = driver.findElement(By.linkText("Confirm"));
		// This will scroll the page till the element is found
		js.executeScript("arguments[0].scrollIntoView();", Element1);
		// click Confirm button
		driver.findElement(By.xpath("//input[contains(@class,'button-1 confirm-order-next-step-button')]")).click();

		// Verify the Thank You Page
		String expectedResult = "Thank you";
		String actualResult = driver.findElement(By.xpath("//h1[contains(.,'Thank you')]")).getText();
		Assert.assertEquals(actualResult, expectedResult);
	     }
	}

	@BeforeClass
	public void beforeClass() throws IOException {
		DesiredCapabilities capabilities = new DesiredCapabilities();
		capabilities.setCapability(MobileCapabilityType.DEVICE_NAME, "Debasis");
		capabilities.setCapability(MobileCapabilityType.PLATFORM_NAME, "Android");
		capabilities.setCapability(MobileCapabilityType.BROWSER_NAME, "Chrome");
		driver = new AndroidDriver(new URL("http://0.0.0.0:4723/wd/hub"), capabilities);
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		driver.get("http://demowebshop.tricentis.com/");
		/*
		 * driver.get("http://demowebshop.tricentis.com/login");
		 * driver.findElement(By.xpath("//input[@id='Email']")).sendKeys(
		 * "Debasis123@gmail.com");
		 * driver.findElement(By.xpath("//input[@id='Password']")).sendKeys("test123");
		 * driver.hideKeyboard();
		 * driver.findElement(By.xpath("//input[@value='Log in']")).click();
		 */
	}

	@AfterClass
	public void afterClass() {
		driver.close();
	}

}
