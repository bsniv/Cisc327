package BL;

import java.util.LinkedList;

import DAL.Data;
import SharedClasses.Transaction;
import SharedClasses.User;

public class BL {

	LinkedList<Transaction> list;
	Data data;
	LinkedList<User> deletedAccounts;
	
	public BL(Data data) {
		list = new LinkedList<Transaction>(); //store user inputs in list
		deletedAccounts =  new LinkedList<User>(); 
		this.data = data;
	}
	
	public void writeTransactions(){
		data.writeTransactions(list);
	}
	
	public boolean addTransaction(Transaction t){
		for(User user : deletedAccounts){
			if (t.getToAccountNumber().equals(user.getUsername()) || t.getFromAccountNumber().equals(user.getUsername())){
				System.out.println("deleted account is not allowed to do transactions");
				return false;
			}
		}
		list.add(t);
		return true;
	}

	public boolean validateFreeAccountNumber(String accountNumber){
		LinkedList<User> users = data.readAccountFile();
		for(User user : users){
			if (accountNumber.equals(user.getUsername()))
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
