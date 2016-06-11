package roughWork;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

public class LpaString {

	public static void main(String[] args) {
		
		WebDriver driver= new FirefoxDriver();
		driver.get("https://facebook.com");
		
		String lpa = "Infinite Computer So  (-71.23%)";
		String lpa_text[] = lpa.split("\\(");
		System.out.println(lpa_text[0].trim());
		//System.out.println(lpa_text[1].split("\\)")[0].split("%")[0] );
		String temp = lpa_text[1].split("\\)")[0];
		System.out.println(temp);
		String percentageChange= temp.split("%")[0];
		System.out.println(percentageChange);
		
		
		

	}

}
