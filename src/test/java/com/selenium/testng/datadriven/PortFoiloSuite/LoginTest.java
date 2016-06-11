//@DataProvider is removed from every test case and created a common TestDataProvider class

package com.selenium.testng.datadriven.PortFoiloSuite;

import java.util.Hashtable;

import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.selenium.testng.datadriven.TestBase;
import com.selenium.testng.datadriven.util.Constants;
import com.selenium.testng.datadriven.util.TestDataProvider;
 


public class LoginTest extends TestBase{
	
	@BeforeTest
	public void initLogs(){
		initLogs(this.getClass());
		
	}
	
	
	@Test(dataProviderClass=TestDataProvider.class,dataProvider="suiteADataProvider")
	public void loginTest(Hashtable<String, String> table){
		/*System.out.println("Test 1");
		Xls_Reader xls = new Xls_Reader("D:\\Se\\FrameWorks\\Module20_Xls\\Suite.xlsx");
		System.out.println(Utility.isSuiteRunnable("SuiteA", xls));	
		System.out.println(Utility.isSuiteRunnable("SuiteB", xls));
		System.out.println(Utility.isSuiteRunnable("SuiteC", xls));
		
		Xls_Reader xls1 = new Xls_Reader("D:\\Se\\FrameWorks\\Module20_Xls\\SuiteA.xlsx");
		System.out.println(Utility.isTestCaseRunnable("Test1", xls1));
		table.get("Runmode"); */
		
		APPLICATION_LOG.debug("Running the test1");
		validateRunmodes("loginTest",Constants.FIRST_SUITE, table.get(Constants.RUNMODE_COL));
				
		doLogin(table.get(Constants.BROWSER_COL), table.get(Constants.USERNAME_COL),table.get(Constants.PASSWORD_COL));
		
		boolean signOutLink=iselementPresent("signOut_xpath");
		
		if(!((table.get(Constants.EXPEXTEDRESULT_COL).equals("SUCCESS")) && signOutLink))
			Assert.fail("Not able to login with correct credentials");
			
		else if(table.get(Constants.EXPEXTEDRESULT_COL).equals("FAILURE") && signOutLink){
			Assert.fail("Login with wrong credentials ");
		}

	}

	@AfterMethod
	public void close(){
		quit();
	}
	
/*	@DataProvider
	public Object[][] getData(){
		Xls_Reader xls = new Xls_Reader(prop.getProperty("xlsfileLocation")+"\\SuiteA.xlsx");
		return Utility.getData("Test1", xls);
		
	}
*/
}
