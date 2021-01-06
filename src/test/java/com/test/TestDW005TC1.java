package com.test;

import org.testng.annotations.Test;

import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.remote.MobileCapabilityType;
import io.appium.java_client.touch.WaitOptions;
import io.appium.java_client.touch.offset.PointOption;

import org.testng.annotations.BeforeClass;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.Assert;
import org.testng.annotations.AfterClass;

public class TestDW005TC1 {
	public AndroidDriver driver;
	
  @Test
  public void verifyProduct() {
		driver.findElement(By.xpath("//span[@class='icon']")).click();
		driver.findElement(By.xpath("//a[contains(text(),'Computers')]/following-sibling::span")).click();
		driver.findElement(
				By.xpath("//li[@class='active']//ul[@class='sublist firstLevel']//a[contains(text(),'Desktops')]"))
				.click();
		driver.findElement(By.xpath("(//input[@value='Add to cart'])[1]")).click();
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		// Swipe action
		TouchAction act = new TouchAction(driver);
		Dimension d = driver.manage().window().getSize(); // To get Size(height and width) of the screen
		int width = d.width;
		int heigth = d.height;
		int x1 = width / 2;
		int y1 = 4 * heigth / 5;
		int x2 = width / 2;
		int y2 = heigth / 5;
		act.press(PointOption.point(x1, y1)).waitAction(WaitOptions.waitOptions(Duration.ofSeconds(1)))
				.moveTo(PointOption.point(x2, y2)).release().perform();
		String expectedResult = "Availability:";
		String actualResult = driver.findElement(By.xpath("//span[@class='label'][contains(.,'Availability:')]"))
				.getText();
		Assert.assertEquals(actualResult, expectedResult);
		String processor = "Availability:";
		String actualLavel = driver.findElement(By.xpath("//label[contains(.,'Processor')]")).getText();
		Assert.assertEquals(actualLavel, processor);

  }
  @BeforeClass
  public void beforeClass() throws MalformedURLException {
	  DesiredCapabilities capabilities = new DesiredCapabilities();

		capabilities.setCapability(MobileCapabilityType.DEVICE_NAME, "Debasis");
		capabilities.setCapability(MobileCapabilityType.PLATFORM_NAME, "Android");
		capabilities.setCapability(MobileCapabilityType.BROWSER_NAME, "Chrome");
		driver = new AndroidDriver(new URL("http://0.0.0.0:4723/wd/hub"), capabilities);
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		driver.get("http://demowebshop.tricentis.com");
  }

  @AfterClass
  public void afterClass() {
	  driver.close();
  }

}
