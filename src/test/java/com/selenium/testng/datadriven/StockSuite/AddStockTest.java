package com.selenium.testng.datadriven.StockSuite;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Hashtable;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.selenium.testng.datadriven.TestBase;
import com.selenium.testng.datadriven.util.Constants;
import com.selenium.testng.datadriven.util.ErrorUtil;
import com.selenium.testng.datadriven.util.TestDataProvider;


public class AddStockTest extends TestBase{
	@BeforeTest
	public void initLogs(){
		initLogs(this.getClass());
	}
	
	@Test(dataProviderClass=TestDataProvider.class,dataProvider="suiteBDataProvider")
	public void addStockTest(Hashtable<String, String> table) throws InterruptedException{	

		validateRunmodes("AddStockTest", Constants.SECOND_SUITE, table.get(Constants.RUNMODE_COL));
		
		doDefaultLogin(table.get(Constants.BROWSER_COL));
		
		try{
			Assert.assertTrue(verifyTitle("loginPageTilte"), "Tiltles did not match");
		}catch(Throwable t){
			ErrorUtil.addVerificationFailure(t);
		}
				
		click("addStock_xpath");
		//driver.findElement(By.xpath(prop.getProperty("addStock_xpath"))).sendKeys(Keys.ENTER);
		input("stockName_xpath", table.get("StockName"));
		Thread.sleep(4000);
		click("calender_xpath"); 
		//String month_yearDisplayed = driver.findElement(By.xpath("//*[@id='datepicker']/table/tbody/tr[1]/td[3]/div")).getText();//getText("monthYearText_xpath");
		//System.out.println(month_yearDisplayed);
		
		Date currentDate = new Date();
		
		String date = table.get("DateOfPurchase");  // o/p is string ...change the format
		Date dateToBeSelected = null;
		SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
				
		try {
			dateToBeSelected = formatter.parse(date);
			System.out.println(dateToBeSelected);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		String month=new SimpleDateFormat("MMMM").format(dateToBeSelected);
		
		Calendar cal = Calendar.getInstance();
		cal.setTime(dateToBeSelected);
		int year = cal.get(Calendar.YEAR);
		int day  = cal.get(Calendar.DAY_OF_MONTH);
	
		String month_yearExpected = month+" "+year;
		System.out.println(month_yearExpected);
		
		while(true){
			//String month_yearDisplayed = getText("monthYearText_xpath");
			String month_yearDisplayed = driver.findElement(By.xpath("//*[@id='datepicker']/table/tbody/tr[1]/td[3]/div")).getText();
			//System.out.println(month_yearDisplayed); 
			if(month_yearDisplayed.equals(month_yearExpected))
			break;
				 
			if(currentDate.after(dateToBeSelected))
				 driver.findElement(By.xpath("//div[@id='datepicker']/table/tbody/tr[1]/td[2]/button")).click();
			else
				 //click("calcFront_xapth");
				  driver.findElement(By.xpath("//div[@id='datepicker']/table/tbody/tr[1]/td[4]/button")).click();
		}
		driver.findElement(By.xpath("//td[text()='"+day+"']")).click();
		
		input("quantity_xpath", table.get("Quantity"));
		input("purchasePrice_xpath", table.get("PurchasePrice"));
		//click("addStockButton_xpath");
		
		Actions act= new Actions(driver);
		WebElement stockButton = driver.findElement(By.xpath("html/body/div[9]/form/div[2]/div/div[1]/div[6]/div/input"));
		act.moveToElement(stockButton).build().perform();
		stockButton.sendKeys(Keys.ENTER);
		//stockButton.click();
		//stockButton.click();
		/*
		
		stockButton.sendKeys(Keys.ENTER);
		stockButton.sendKeys(Keys.ENTER);*/
				
	}
	
	@AfterMethod
	public void close(){
		quit();
	}
	

}
