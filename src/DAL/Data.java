package DAL;

import java.io.FileWriter;
import SharedClasses.Transaction;
import java.io.*;
import java.util.LinkedList;

/**
 * data class - responsible for data fetching and updating
 *
 */
public class Data {

    private static String transactionFilePath="";
    public static void initUserFile(){
        Data.transactionFilePath = System.getProperty("user.dir")+"\\transactions.txt";
    }

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



}
