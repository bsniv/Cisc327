package BL;

import java.util.Scanner;
import qbasic.qbasic;
import PL.ATM;

//Startup of system, takes user input of "qbasic" follwed by the filenames of the VAL file and the TS file
public class QbasicStartup {
	
	public static void main (String[] args) {
		Scanner startup = new Scanner(System.in).useDelimiter("\\s");
		System.out.println("Welcome.");
		String input = startup.next();
		if (input == "qbasic") {
			String VALfile = startup.next();
			String TSfile = startup.next();
			qbasic QBStart = new qbasic(VALfile, TSfile); //set qbasic object
			ATM.main(); //start ATM session
		}
	}
	main();
}
