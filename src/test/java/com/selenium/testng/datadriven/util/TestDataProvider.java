package com.selenium.testng.datadriven.util;

import java.lang.reflect.Method;

import org.testng.annotations.DataProvider;

import com.selenium.testng.datadriven.TestBase;

// it can be public class TestDataProvider extends TestBase 
// or
// prop object can be accessed as TestBase.prop.getProperty()
public class TestDataProvider{
	@DataProvider(name="suiteADataProvider")
	public static Object[][] getDataSuiteA(Method m){
		TestBase.init();
		Xls_Reader xls1 = new Xls_Reader(TestBase.prop.getProperty("xlsfileLocation")+Constants.FIRST_SUITE +".xlsx");
		return Utility.getData(m.getName(), xls1);		
	}
	
	@DataProvider(name="suiteBDataProvider")
	public static Object[][] getDataSuitB(Method m){
		TestBase.init();
		Xls_Reader xls1 = new Xls_Reader(TestBase.prop.getProperty("xlsfileLocation")+Constants.SECOND_SUITE+".xlsx");
		return Utility.getData(m.getName(), xls1);	
	}
	 
}
