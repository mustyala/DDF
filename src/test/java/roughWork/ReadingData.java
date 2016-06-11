package roughWork;

import com.selenium.testng.datadriven.util.Constants;
import com.selenium.testng.datadriven.util.Xls_Reader;


public class ReadingData {
	
	public static void main(String[] args){
	
		Xls_Reader xls = new Xls_Reader("D:\\Se\\FrameWorks\\Module20_Xls\\SuiteA.xlsx");
		
		String testName = "test2";
		
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
        int ColStartRowNum =  testcaseRowNum+1;   // row at which data starts
        
        int testRows = 1;
        while(!xls.getCellData(Constants.DATA_SHEET, 0, dataStartRowNum+testRows).equals("")){
        	testRows++;
        }
		System.out.println("Total rows of data of given test case are " + testRows );
        int testCols = 1;
        while(!xls.getCellData(Constants.DATA_SHEET, testCols, ColStartRowNum).equals("")){
        	testCols++;
        }
    	System.out.println("Total cols of data of given test case are " + testCols );
    	
    	Object data[][] = new Object[testRows][testCols];
    	for(int rNum = dataStartRowNum;rNum < dataStartRowNum+testRows; rNum++){
    		for(int cNum=0; cNum< testCols ;cNum++ ){
    		//	data[rNum][cNum] = xls.getCellData(Constants.DATA_SHEET, rNum, cNum);
    			System.out.println(xls.getCellData(Constants.DATA_SHEET, cNum,rNum));
    			
      		}
    	}
	}

}
