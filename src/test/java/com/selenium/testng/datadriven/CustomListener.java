package com.selenium.testng.datadriven;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Hashtable;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.testng.IInvokedMethod;
import org.testng.IInvokedMethodListener;
import org.testng.ISuite;
import org.testng.ISuiteListener;
import org.testng.ITestResult;
import org.testng.TestListenerAdapter;

import com.selenium.testng.datadriven.util.Constants;
import com.selenium.testng.datadriven.util.ErrorUtil;
import com.selenium.testng.datadriven.util.Xls_Reader;

public class CustomListener extends TestListenerAdapter implements IInvokedMethodListener, ISuiteListener{
    public static Hashtable<String, String> resultTable;
	public static String resultFolderName;
	public static String resultFilePath;
	public static ArrayList<String> keys;
	
	public void onTestFailure(ITestResult tr){
		
		//TestBase.APPLICATION_LOG.debug("Fail - " + tr.getName());
		//report(tr.getName(), tr.getThrowable().getMessage());
		List<Throwable> verificationFailures = ErrorUtil.getVerificationFailures();
		String errMsg ="";
		for(int i=0; i<verificationFailures.size();i++){
			errMsg = errMsg+verificationFailures.get(i).getMessage();
			report(tr.getName(), tr.getThrowable().getMessage());
			
		}
	}
	
	
	public void onTestSkipped(ITestResult tr){
		//TestBase.APPLICATION_LOG.debug("Skipped - " + tr.getName());
		report(tr.getName(), tr.getThrowable().getMessage());
	}
	
	public void onTestSuccess(ITestResult tr){
		//TestBase.APPLICATION_LOG.debug("Success - " + tr.getName());
		report(tr.getName(), "PASS");
	}


	@Override
	public void afterInvocation(IInvokedMethod arg0, ITestResult arg1) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void beforeInvocation(IInvokedMethod arg0, ITestResult arg1) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void onStart(ISuite suite) {
		System.out.println("Starting suite " + suite.getName());
		if(resultTable==null){
			// initialize hash table and array list
			resultTable = new Hashtable<String, String>();
		    keys = new ArrayList<String>();
		}
		if(resultFolderName==null){  
			Date d = new Date();
			resultFolderName=d.toString().replace(":","_");
			File f = new File(System.getProperty("user.dir")+"//target//reports//"+resultFolderName);
			f.mkdir();
			// change
			resultFilePath=System.getProperty("user.dir")+"//target//reports//"+resultFolderName+"//Report.xlsx";
			File src = new File(System.getProperty("user.dir")+"//target//reports//ReportTemplate.xlsx");
			File dest = new File(resultFilePath);
			try {
				FileUtils.copyFile(src, dest);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	@Override
	public void onFinish(ISuite suite) {
		if(resultTable !=null){
			System.out.println("Finishing suite "+ suite.getName());
			System.out.println(resultTable);
			System.out.println(keys);
		//	resultTable =null;
			// write results in xlsx
			if(!suite.getName().equals(Constants.ROOT_SUITE)){
				Xls_Reader xls = new Xls_Reader(resultFilePath);
				xls.addSheet(suite.getName());
				// add the results in the sheet
				
				// create col Names
				xls.setCellData(suite.getName(), 0, 1, "Test Case");
				xls.setCellData(suite.getName(), 1, 1, "Result");
				
				for(int i=0;i<keys.size();i++){
					String key = keys.get(i);
					String result = resultTable.get(key);
					xls.setCellData(suite.getName(), 0, i+2, key);
					xls.setCellData(suite.getName(), 1, i+2, result);

				}
				
			}
			
			resultTable =null;
			keys=null;
		}
		
	}
	
	
	public void report(String name, String result) {
		int iteration_number=1;
		while(resultTable.containsKey(name + " Iteration " +iteration_number)){
			iteration_number++;
		}
		keys.add(name+" Iteration "+ iteration_number);
		resultTable.put(name+" Iteration "+iteration_number, result);

	}

	
}
