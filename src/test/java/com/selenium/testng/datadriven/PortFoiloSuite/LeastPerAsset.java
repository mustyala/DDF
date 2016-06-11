package com.selenium.testng.datadriven.PortFoiloSuite;

import java.util.Hashtable;

import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.selenium.testng.datadriven.TestBase;
import com.selenium.testng.datadriven.util.Constants;
import com.selenium.testng.datadriven.util.TestDataProvider;


public class LeastPerAsset extends TestBase{
	
	@BeforeTest
	public void initLogs(){
		initLogs(this.getClass());
	}
	
	
	@Test(dataProviderClass=TestDataProvider.class,dataProvider="suiteADataProvider")
	public void leastPerAsset(Hashtable<String, String> table){
		APPLICATION_LOG.debug("Running LeastPerAsset ");
		validateRunmodes("LeastPerAsset", Constants.FIRST_SUITE, table.get(Constants.RUNMODE_COL));
		
		// login with default credentials
		doDefaultLogin(table.get(Constants.BROWSER_COL));
		
		// Verify the titles after login into portFolio test
	//	Assert.assertTrue(verifyTitle(prop.getProperty("portFolioTilteAfterLogin")), "Tiltes do not match Got tilte as - " + driver.getTitle());
		System.out.println(driver.getTitle());
		String lpa = prop.getProperty("leastPerAssetText_xpath");
		String lpa_text[] = lpa.split("\\(");
		String companyName = lpa_text[0].trim();
		String temp = lpa_text[1].split("\\)")[0];
		String percentageChange= temp.split("%")[0];
		
		Assert.assertTrue(iselementPresent("//a[text()='"+companyName+"']"), "Least per asset company is not found");
		Assert.assertTrue(iselementPresent("//td/span[text='"+percentageChange+"']"), "LPA is not found");
		
		// search inside table of stocks and mutual funds -module 15 and module 17 reference
		
	}

	@AfterMethod
	public void close(){
		quit();
	}
}
