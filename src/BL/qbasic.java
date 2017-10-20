package BL;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.BufferedReader;
import java.io.FileReader;

//Object class for the system
//Opens the Valid Accounts List File and readies the Transaction Summary file
public class qbasic {
	
	private BufferedReader VALfile; //Valid Accounts List file, ready to be read
	private File TSfile; //Transaction Summarry file, ready to be written
	
	//constructor for class
	public qbasic(String VALfile, String TSfile) {
		setTSfile(TSfile);
		setVALfile(VALfile);
	}
	
	//Sets the Valid Accounts List file attribute
	public void setVALfile(String VALfile) {
		BufferedReader file = null;
		try { file = new BufferedReader(new FileReader(VALfile)); }
		catch (FileNotFoundException e) { System.out.println("Error; File not Found"); }
		this.VALfile = file;
	}
	
	//Sets the Transaction Summarry file attribute
	public void setTSfile(String TSfile) {
		File TS = new File(TSfile);
		this.TSfile = TS;
	}
}
