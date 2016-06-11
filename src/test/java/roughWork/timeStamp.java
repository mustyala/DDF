package roughWork;

import java.util.Date;

public class timeStamp {

	public static void main(String[] args) {
		Date d = new Date();
		System.out.println(d);
		System.out.println(d.toString().replace(":","_"));
	}

}
