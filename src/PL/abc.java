package PL;

import java.util.LinkedList;

import DAL.Data;
import SharedClasses.Transaction;
import SharedClasses.User;

public class abc {

	public abc() {
		// TODO Auto-generated constructor stub
	}

	public static void main(String[] args) {
		Data data = new Data();
		data.initUserFile();
		data.saveNewUser(new User("admin2","admin123"));
		User user2 = data.loadUser("admin2");
		System.out.println(user2.getUsername());
		
		LinkedList<Transaction> list = new LinkedList<Transaction>();
		list.add(new Transaction("abc","def"));
		list.add(new Transaction("123","456"));
		data.writeTransactions(list);
	}

}
