package PL;

import java.util.LinkedList;

import DAL.Data;
import SharedClasses.Transaction;
import SharedClasses.Transaction.TransactionCodes;
import SharedClasses.User;

public class abc {

	public abc() {
		// TODO Auto-generated constructor stub
	}

	public static void main(String[] args) {
		Data data = new Data();
		data.initUserFile();
		LinkedList<Transaction> list = new LinkedList<Transaction>();
		list.add(new Transaction(TransactionCodes.NEW,"abc", 100, "def", "master"));
		list.add(new Transaction(TransactionCodes.XFR,"jjj", 200, "abc", "master"));
		data.writeTransactions(list);
	}

}
