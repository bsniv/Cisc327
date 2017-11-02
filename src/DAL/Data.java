package DAL;

import java.io.FileWriter;
import java.nio.file.Files;
import java.nio.file.Paths;

import SharedClasses.Transaction;
import SharedClasses.User;

import java.io.*;
import java.util.LinkedList;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * data class - responsible for data fetching and updating
 *
 */
public class Data {

    private static String transactionFilePath="";
    private static String userFilePath="";

    /**
     * writes all the Transactions LinkedList to the transaction file
     * @param transactions
     */
    public static void writeTransactions(LinkedList<Transaction> transactions) {
        try {
        	FileWriter writer = new FileWriter(transactionFilePath, false);
        	PrintWriter printWriter = new PrintWriter(writer);
        	for (Transaction t : transactions)
        		printWriter.printf("%s" + "%n", t.toString());
        	printWriter.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public LinkedList<User> readAccountFile(){
    	LinkedList<User> users = new LinkedList<User>();
		try (BufferedReader br = new BufferedReader(new FileReader(userFilePath))) {

			String line;
			line = br.readLine();
			while (line != null && !line.equals("0000000")) {
				if (!checkOnlyNumbers(line))
					throw new RuntimeException("user file corrupted");
				users.add(new User(line,line));
				line = br.readLine();
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
    	
    	return users;
    }
    
    private static boolean checkOnlyNumbers(String s){
		if(s.equals(""))
			return false;
		String temp="0123456789";
		for(int i=0;i<s.length();i++){
			if(temp.indexOf(s.charAt(i))==-1)
				return false;
		}
		return true;
	}

    public static void setTransactionFilePath(String path){
    	if (path == null)
    		transactionFilePath = System.getProperty("user.dir")+"\\transactions.txt";
    	else
    		transactionFilePath = System.getProperty("user.dir")+"\\"+path;
    }
    
    public static void setUserFilePath(String path){
    	if (path == null)
    		userFilePath = System.getProperty("user.dir")+"\\users.txt";
    	else
    		userFilePath = System.getProperty("user.dir")+"\\"+path;
    }

}
