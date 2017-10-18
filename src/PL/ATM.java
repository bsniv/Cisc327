package PL;

import java.util.LinkedList;

import DAL.Data;
import SharedClasses.Transaction;
import SharedClasses.Transaction.TransactionCodes;

public class ATM {

	public static void main(String[] args) {
		boolean loggedIn = false; //Is the user logged in?
		boolean isAgent = false; //Is the user an agent?
		Data data = new Data();
		data.initUserFile();
		LinkedList<Transaction> list = new LinkedList<Transaction>(); //store user inputs in list
		
		while (loggedIn = false) //wait until the user logins before accepting any other input
		{
			System.out.print("Please login:");
			String input = System.console().readLine();
			if (input == "login")
			{
				while (loggedIn = false)
				{
					System.out.print("Enter 'ATM' for a normal session, or 'agent' for an agent session:");
					input = System.console().readLine();
					if (input == "ATM")
					{
						loggedIn = true;
						isAgent = false;
					}
					else if (input == "agent")
					{
						loggedIn = true;
						isAgent = true;
					}
			}
		}
			
		while (loggedIn = true)
		{
			if (input == "logout") //upon logout, write EOS and write the Transactions file
			{
				loggedIn = false;
				list.add(new Transaction(TransactionCodes.EOS, "000", "000", "000", "***"));
				data.writeTransactions(list);
				System.exit(0);
			}
			
			if (input == "createacct") //Creating account
			{
				if (isAgent)
				{
					System.out.print("Enter the new account's number:"); //ask for the account number
					String accountNumber = System.console().readLine();
					
					System.out.print("Enter the new account's name:"); //ask for the account name
					String accountName = System.console().readLine();
					
					list.add(new Transaction(TransactionCodes.NEW, accountNumber, "000", "000", accountName));
				}
				else
					System.out.print("Sorry, that is a privileged command");
			}
			
			if (input == "deleteacct") //Deleting account
			{
				if (isAgent)
				{
					System.out.print("Enter the account's number:"); //ask for the account number
					String accountNumber = System.console().readLine();
					
					System.out.print("Enter the account's name:"); //ask for the account name
					String accountName = System.console().readLine();
					
					list.add(new Transaction(TransactionCodes.DEL, accountNumber, "000", "000", accountName));
				}
				else
					System.out.print("Sorry, that is a privileged command");
			}
			
			if (input == "deposit") //Depositing money
			{
				System.out.print("Enter the account's number:"); //ask for the account number
				String accountNumber = System.console().readLine();
				
				System.out.print("Enter the amount to be deposited"); //ask for the deposited amount
				String amount = System.console().readLine();
				
				if (Integer.parseInt(amount) > 100000 && isAgent == false)
					System.out.print("Sorry, only agents can deposit more than $1000.00 at once");
				else if (Integer.parseInt(amount) > 99999999 && isAgent == true)
					System.out.print("Sorry, not event agents can deposit more than $999,999.99 at once");
				else
					list.add(new Transaction(TransactionCodes.DEP, accountNumber, amount, "000", "***"));
			}
			
			if (input == "withdraw") //Withdrawing money
			{
				System.out.print("Enter the account's number:"); //ask for the account number
				String accountNumber = System.console().readLine();
				
				System.out.print("Enter the amount to be deposited"); //ask for the deposited amount
				String amount = System.console().readLine();
				
				if (Integer.parseInt(amount) > 100000 && isAgent == false)
					System.out.print("Sorry, only agents can withdraw more than $1000.00 at once");
				else if (Integer.parseInt(amount) > 99999999 && isAgent == true)
					System.out.print("Sorry, not event agents can withdraw more than $999,999.99 at once");
				else
					list.add(new Transaction(TransactionCodes.WDR, accountNumber, amount, "000", "***"));
				
			}
			
			if (input == "transfer") //Transferring money
			{
				System.out.print("Enter the account that is being transferred from"); //ask for first account
				String accountNumber1 = System.console().readLine();
				
				System.out.print("Enter the account that is being transferred to"); //ask for second account
				String accountNumber2 = System.console().readLine();
				
				System.out.print("Enter the amount to be transferred");
				String amount = System.console().readLine();
				
				if (Integer.parseInt(amount) > 100000 && isAgent == false)
					System.out.print("Sorry, only agents can withdraw more than $1000.00 at once");
				else if (Integer.parseInt(amount) > 99999999 && isAgent == true)
					System.out.print("Sorry, not event agents can withdraw more than $999,999.99 at once");
				else
					list.add(new Transaction(TransactionCodes.XFR, accountNumber1, amount, accountNumber2, "***"));
			}
		}
		
	}

	}
}