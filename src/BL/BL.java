package BL;

import java.util.LinkedList;

import DAL.Data;
import SharedClasses.Transaction;

public class BL {

	LinkedList<Transaction> list;
	Data data;

	public BL(Data data) {
		list = new LinkedList<Transaction>(); //store user inputs in list
		this.data = data;
		data.initUserFile();
	}
	
	public void writeTransactions(){
		data.writeTransactions(list);
	}
	
	public void addTransaction(Transaction t){
		list.add(t);
	}

}
