package com.selenium.testng.datadriven;

import java.io.FileInputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.FileAppender;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.PatternLayout;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.Assert;
import org.testng.SkipException;


import com.selenium.testng.datadriven.util.Constants;
import com.selenium.testng.datadriven.util.Utility;
import com.selenium.testng.datadriven.util.Xls_Reader;



public class TestBase {

	public WebDriver driver=null;
	public static Properties prop;
	//public RemoteWebDriver driver1=null;
	
	public Logger APPLICATION_LOG = null; //Logger.getLogger("devpinoyLogger");
	
	public void initLogs(Class<?> class1){
		FileAppender appender = new FileAppender();
		// configure the appender here, with file location,  etc
		appender.setFile(System.getProperty("user.dir")+"//target//reports//"+CustomListener.resultFolderName+"//"+class1.getName()+".log");
		appender.setLayout(new PatternLayout("%d %-5p [%c{1}] %m%n"));
		appender.setAppend(false);
		appender.activateOptions();
		
		APPLICATION_LOG = Logger.getLogger(class1);
		APPLICATION_LOG.setLevel(Level.DEBUG);
		APPLICATION_LOG.addAppender(appender);
	}
	
	public static void init() {
		if(prop == null){
		String path = System.getProperty("user.dir")+"\\src\\test\\resources\\project.properties";
		prop = new Properties();
        
		try{		
			FileInputStream fs = new FileInputStream(path);
	    prop.load(fs);
		} catch(Exception e){
			e.printStackTrace();
			
		}
		
		}
	}
	
	public void validateRunmodes(String testName, String suiteName,String dataRunmode){
		
		APPLICATION_LOG.debug("Validating Run mode for  " + testName + " in suite - " + suiteName);
		init();
		
		// suite run mode 
		boolean suiteRunmode = Utility.isSuiteRunnable(suiteName, new Xls_Reader(prop.getProperty("xlsfileLocation")+Constants.SUITE_SHEET+".xlsx"));
		boolean testRunmode = Utility.isTestCaseRunnable(testName, new Xls_Reader(prop.getProperty("xlsfileLocation")+suiteName+".xlsx"));
	    //System.out.println(testRunmode);
		
		boolean dataSetRunmode = false;
		if(dataRunmode.equals(Constants.RUNMODE_YES))
		    dataSetRunmode = true;
		
		if(!(suiteRunmode && testRunmode && dataSetRunmode)){
		APPLICATION_LOG.debug("Skipping the test" + testName + "inside the siute" + suiteName);
		throw new SkipException("Skipping " + testName + "in side " + suiteName);	
		}
	    
	}
	
	

	/********************************Generic functions ****************************/
	
	public void openBrowser(String browserName){
		
		
		/*if(browserName.equals("Mozilla"))
		driver = new FirefoxDriver();
	else if(browserName.equals("Chrome")){
		System.setProperty("webdriver.chrome.driver",prop.getProperty("chromedriverexe"));
		driver = new ChromeDriver();
	}else if(browserName.equals("ie")){
		System.setProperty("webdriver.internetexplorer.driver", prop.getProperty("iedriverexe"));
		driver = new InternetExplorerDriver();			
	}*/
	
		// with grid , we have to use remote driver
		DesiredCapabilities cap = new DesiredCapabilities();
		try{
		if(browserName.equals("Mozilla")){
			cap = DesiredCapabilities.firefox();
			cap.setBrowserName("firefox");			
		}else if(browserName.equals("Chrome")){
			cap = DesiredCapabilities.chrome();
			cap.setBrowserName("Chrome");
		}
		cap.setPlatform(Platform.ANY);

		try {
			RemoteWebDriver driver = new RemoteWebDriver(new URL("http://localhost:4444/wd/hub"), cap);
		} catch (Exception e) {
		   Assert.fail("Not able to open browser - " + e.getMessage());
		}
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		}catch(Exception e){
			Assert.fail("Not able to open browser - " + e.getMessage());
		}
	}
	
	public void navigate(String URLKey){
		driver.get(prop.getProperty(URLKey));
	}
	
	public void click(String identifier){
		try{
		if(identifier.endsWith("_xpath"))
		driver.findElement(By.xpath(prop.getProperty(identifier))).click();
		else if(identifier.endsWith("_id"))
		driver.findElement(By.id(prop.getProperty(identifier))).click();
		else if(identifier.endsWith("_name"))
			driver.findElement(By.name(prop.getProperty(identifier))).click();
	   }catch(NoSuchElementException e){
		Assert.fail("Element not found - " + identifier);
	}
	
	}
	
	public void input(String identifier, String data ){
		try{
			if(identifier.endsWith("_xpath"))
		  driver.findElement(By.xpath(prop.getProperty(identifier))).sendKeys(data);
		else if(identifier.endsWith("_id"))
			driver.findElement(By.id(prop.getProperty(identifier))).sendKeys(data);
		else if(identifier.endsWith("_name"))
			driver.findElement(By.name(prop.getProperty(identifier))).sendKeys(data);
		}catch(NoSuchElementException e){
			Assert.fail("Element not found - " + identifier);
			
		}
	 } 
	
	public boolean verifyTitle(String expectedTitleKey){
		String expectedTitlte = prop.getProperty(expectedTitleKey);
		String actualTilte = driver.getTitle();
		if(expectedTitlte.equals(actualTilte))
			return true;
		else
			return false;
	}
	
	public boolean iselementPresent(String identifier){
		int size=0;
		if(identifier.endsWith("_xpath"))
			size = driver.findElements(By.xpath(prop.getProperty(identifier))).size();
		else if(identifier.endsWith("_id"))
			size = driver.findElements(By.id(prop.getProperty(identifier))).size();
		else if(identifier.endsWith("_name"))
			size = driver.findElements(By.name(prop.getProperty(identifier))).size();
		else
			size = driver.findElements(By.xpath(identifier)).size();
		
		if(size>0)
			return true;
		else
			return false;
	}
	
	public String getText(String identifier){
		String text = "";
		if(identifier.endsWith("_xpath"))
			text = driver.findElement(By.xpath(prop.getProperty(identifier))).getText();
		else if(identifier.endsWith("_id"))
			text = driver.findElement(By.id(prop.getProperty(identifier))).getText();
		else if(identifier.endsWith("_name"))
			text = driver.findElement(By.name(prop.getProperty(identifier))).getText();
		return text;
	}
	
	public void quit(){
		if(driver!=null){
			driver.quit();
		}
		driver =null;
	}
	
	public void doDefaultLogin(String browser){
		doLogin(browser, prop.getProperty("defaultUsername"), prop.getProperty("defaultPassword"));
	}
	
	/*********************Application Specific****************************/
	public void doLogin(String browser, String username, String password){
		openBrowser(browser);
		navigate("testSiteURL");
		Assert.assertTrue(iselementPresent("money_link_xpath"), "Element is not found - money_link_xpath");
		click("money_link_xpath");
		click("myportfolio_link_xpath");
		Assert.assertTrue(verifyTitle("portFolioPage"), "Titles do not match. Got tilte as -"+ driver.getTitle());
		input("emailId_xpath", username);
		click("emailSubmitContinue_xpath");
		input("emailPassword_xpath", password);
		click("loginSubmit_xpath");
	}
	
	public void checkLeastPerAsset(String browser){
		
		
	}
}