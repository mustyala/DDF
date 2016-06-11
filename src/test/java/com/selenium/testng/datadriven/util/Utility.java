package com.selenium.testng.datadriven.util;

import java.util.Hashtable;

public class Utility {
	public static boolean isSuiteRunnable(String suiteName, Xls_Reader xls){
		int rows = xls.getRowCount(Constants.SUITE_SHEET);
		//System.out.println("Total rows in a suite " + rows);
		
		for(int rNum=2; rNum<=rows; rNum++){
			String data= xls.getCellData(Constants.SUITE_SHEET, Constants.SUITENAME_COL, rNum);
			if(data.equalsIgnoreCase(suiteName)){
				String runmode= xls.getCellData(Constants.SUITE_SHEET, Constants.RUNMODE_COL, rNum);
				if(runmode.equals(Constants.RUNMODE_YES))
					return true;
				else
					return false; //default
			}
				
		}
				
		return false;
	}
	
	public static boolean isTestCaseRunnable(String testCaseName, Xls_Reader xls){
		
		int rows = xls.getRowCount(Constants.TESTCASES_SHEET);
		
		    for(int rNum=2; rNum<rows; rNum++){
			String data = xls.getCellData(Constants.TESTCASES_SHEET, Constants.TESTCASE_NAME_COL, rNum);
			//System.out.println(data);
			if(data.equalsIgnoreCase(testCaseName)){
				String runmode = xls.getCellData(Constants.TESTCASES_SHEET, Constants.RUNMODE_COL, rNum);
				if(runmode.equalsIgnoreCase(Constants.RUNMODE_YES))
					return true;
				else
					return false;
			}
		}
				
	  return false;
	}

	
	
	public static Object[][] getData(String testName, Xls_Reader xls){
		
		
		int rows = xls.getRowCount(Constants.DATA_SHEET);
		System.out.println("Total rows - " + rows);
		
		// row number for test case
	    int testcaseRowNum = 1;
	    for(testcaseRowNum=1; testcaseRowNum<=rows; testcaseRowNum++){
	    	String testCaseXls =xls.getCellData(Constants.DATA_SHEET, 0, testcaseRowNum);
	    	//System.out.println(testCaseXls);
	    	if(testCaseXls.equalsIgnoreCase(testName)){
	    		break;
	    	}
	     	
	    }
	    System.out.println("Test case " + testName  + " : starts at - " + testcaseRowNum);
	    // rows of data
		
		int dataStartRowNum = testcaseRowNum+2;  // test data start
        int ColStartRowNum =  testcaseRowNum+1;   // row at which column names are preset 
        
        int testRows = 1;
        while(!xls.getCellData(Constants.DATA_SHEET, 0, dataStartRowNum+testRows).equals("")){
        	testRows++;
        }
		System.out.println("Total rows of data of given test case are " + testRows );
        int testCols = 0;
        while(!xls.getCellData(Constants.DATA_SHEET, testCols, ColStartRowNum).equals("")){
        	testCols++;
        }
    	System.out.println("Total cols of data of given test case are " + testCols );
    	
    	Object data[][] = new Object[testRows][1]; // testCols = 1 in hash table only one col and many rows; in each row is one table
    	int r=0;
    	for(int rNum = dataStartRowNum;rNum < dataStartRowNum+testRows; rNum++){
    		Hashtable<String, String> table = new Hashtable<String, String>();
    		for(int cNum=0; cNum< testCols ;cNum++ ){
    			//data[r][cNum] = xls.getCellData(Constants.DATA_SHEET,  cNum,rNum);
    			//System.out.println(xls.getCellData(Constants.DATA_SHEET, cNum,rNum));
    		  table.put(xls.getCellData(Constants.DATA_SHEET,cNum,ColStartRowNum), xls.getCellData(Constants.DATA_SHEET, cNum,rNum)); 
    			
    		}
    		data[r][0] = table;
    		r++;
    	}
    return data;	
	}
}
