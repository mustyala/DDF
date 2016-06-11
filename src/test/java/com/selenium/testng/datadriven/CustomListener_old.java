package com.selenium.testng.datadriven;

import org.testng.IInvokedMethod;
import org.testng.IInvokedMethodListener;
import org.testng.ITestResult;
import org.testng.TestListenerAdapter;

public class CustomListener_old extends TestListenerAdapter implements IInvokedMethodListener{

	public void onTestFailure(ITestResult tr){
		//TestBase.APPLICATION_LOG.debug("Fail - " + tr.getName());
	}
	
	public void onTestSkipped(ITestResult tr){
		//TestBase.APPLICATION_LOG.debug("Skipped - " + tr.getName());
	}
	
	public void onTestSuccess(ITestResult tr){
		//TestBase.APPLICATION_LOG.debug("Success - " + tr.getName());
	}


	@Override
	public void afterInvocation(IInvokedMethod arg0, ITestResult arg1) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void beforeInvocation(IInvokedMethod arg0, ITestResult arg1) {
		// TODO Auto-generated method stub
		
	}
	
}
