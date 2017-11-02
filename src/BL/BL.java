package BL;

import java.util.LinkedList;

import DAL.Data;
import SharedClasses.Transaction;
import SharedClasses.Transaction.TransactionCodes;

public class BL {

	LinkedList<Transaction> list;
	Data data;
	LinkedList<String> deletedAccounts;
	LinkedList<String> createdAccounts;
	
	public BL(Data data) {
		list = new LinkedList<Transaction>(); //store user inputs in list
		deletedAccounts =  new LinkedList<String>(); 
		createdAccounts =  new LinkedList<String>(); 
		this.data = data;
	}
	
	public void writeTransactions(){
		data.writeTransactions(list);
	}
	
	public boolean addTransaction(Transaction t){
		for(String user : deletedAccounts){
			if (t.getToAccountNumber().equals(user) || t.getFromAccountNumber().equals(user)){
				System.out.println("deleted account is not allowed to do transactions");
				return false;
			}
		}
		for(String user : createdAccounts){
			if (t.getToAccountNumber().equals(user) || t.getFromAccountNumber().equals(user)){
				System.out.println("created account is not allowed to do transactions");
				return false;
			}
		}
		if (t.getTC() == TransactionCodes.DEL)
			deletedAccounts.add(t.getFromAccountNumber());
		if (t.getTC() == TransactionCodes.NEW)
			createdAccounts.add(t.getFromAccountNumber());
		list.add(t);
		return true;
	}

	public boolean validateFreeAccountNumber(String accountNumber){
		LinkedList<String> users = data.readAccountFile();
		for(String user : users){
			if (accountNumber.equals(user))
				return false;
		}
		return true;
	}
	
	/* HELPER FUNCTIONS FOR YOUR OWN USE, COPY THEM TO PL*/
	
	static boolean checkOnlyNumbers(String s){
		if(s.equals(""))
			return false;
		String temp="0123456789";
		for(int i=0;i<s.length();i++){
			if(temp.indexOf(s.charAt(i))==-1)
				return false;
		}
		return true;
	}
}
