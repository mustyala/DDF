package com.selenium.testng.datadriven.StockSuite;

import java.util.Hashtable;

import org.testng.annotations.Test;

import com.selenium.testng.datadriven.TestBase;
import com.selenium.testng.datadriven.util.Constants;
import com.selenium.testng.datadriven.util.TestDataProvider;

public class Test2 extends TestBase{
	@Test(dataProviderClass=TestDataProvider.class,dataProvider="suiteBDataProvider")
	public void test2(Hashtable<String, String> table){
		validateRunmodes("Test2", Constants.SECOND_SUITE, table.get(Constants.RUNMODE_COL));
	}

	
	
}
