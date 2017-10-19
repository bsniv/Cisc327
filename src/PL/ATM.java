package PL;

import java.util.LinkedList;
import java.io.*;
import java.util.Scanner;

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
		Scanner s = new Scanner(System.in);
		
		while (!loggedIn) //wait until the user logins before accepting any other input
		{
			System.out.println("Please login:");
			String input = s.nextLine();
			if (input.equals("login"))
			{
				while (!loggedIn)
				{
					System.out.println("Enter 'ATM' for a normal session, or 'agent' for an agent session:");
					input = s.nextLine();
					if (input.equals("ATM"))
					{
						loggedIn = true;
						isAgent = false;
					}
					else if (input.equals("agent"))
					{
						loggedIn = true;
						isAgent = true;
					}
			}
		}
			
		while (loggedIn)
		{
			System.out.print("Please enter a command from the following list:\nlogout\ncreateacct\ndeleteacct\ndeposit\nwithdraw\ntransfer\n");
			input = s.nextLine();
			if (input.equals("logout")) //upon logout, write EOS and write the Transactions file
			{
				loggedIn = false;
				list.add(new Transaction(TransactionCodes.EOS, "000", "000", "000", "***"));
				data.writeTransactions(list);
				System.exit(0);
			}
			
			if (input.equals("createacct")) //Creating account
			{
				if (isAgent)
				{
					System.out.println("Enter the new account's number:"); //ask for the account number
					String accountNumber = s.nextLine();
					
					System.out.println("Enter the new account's name:"); //ask for the account name
					String accountName = s.nextLine();
					
					list.add(new Transaction(TransactionCodes.NEW, accountNumber, "000", "000", accountName));
				}
				else
					System.out.println("Sorry, that is a privileged command");
			}
			
			if (input.equals("deleteacct")) //Deleting account
			{
				if (isAgent)
				{
					System.out.println("Enter the account's number:"); //ask for the account number
					String accountNumber = s.nextLine();
					
					System.out.println("Enter the account's name:"); //ask for the account name
					String accountName = s.nextLine();
					
					list.add(new Transaction(TransactionCodes.DEL, accountNumber, "000", "000", accountName));
				}
				else
					System.out.println("Sorry, that is a privileged command");
			}
			
			if (input.equals("deposit")) //Depositing money
			{
				System.out.println("Enter the account's number:"); //ask for the account number
				String accountNumber = s.nextLine();
				
				System.out.println("Enter the amount to be deposited"); //ask for the deposited amount
				String amount = s.nextLine();
				
				if (Integer.parseInt(amount) > 100000 && !isAgent)
					System.out.println("Sorry, only agents can deposit more than $1000.00 at once");
				else if (Integer.parseInt(amount) > 99999999 && isAgent)
					System.out.println("Sorry, not event agents can deposit more than $999,999.99 at once");
				else
					list.add(new Transaction(TransactionCodes.DEP, accountNumber, amount, "000", "***"));
			}
			
			if (input.equals("withdraw")) //Withdrawing money
			{
				System.out.println("Enter the account's number:"); //ask for the account number
				String accountNumber = s.nextLine();
				
				System.out.println("Enter the amount to be deposited"); //ask for the deposited amount
				String amount = s.nextLine();
				
				if (Integer.parseInt(amount) > 100000 && !isAgent)
					System.out.println("Sorry, only agents can withdraw more than $1000.00 at once");
				else if (Integer.parseInt(amount) > 99999999 && isAgent)
					System.out.println("Sorry, not event agents can withdraw more than $999,999.99 at once");
				else
					list.add(new Transaction(TransactionCodes.WDR, accountNumber, amount, "000", "***"));
				
			}
			
			if (input.equals("transfer")) //Transferring money
			{
				System.out.println("Enter the account that is being transferred from"); //ask for first account
				String accountNumber1 = s.nextLine();
				
				System.out.println("Enter the account that is being transferred to"); //ask for second account
				String accountNumber2 = s.nextLine();
				
				System.out.println("Enter the amount to be transferred");
				String amount = s.nextLine();
				
				if (Integer.parseInt(amount) > 100000 && !isAgent)
					System.out.println("Sorry, only agents can withdraw more than $1000.00 at once");
				else if (Integer.parseInt(amount) > 99999999 && isAgent)
					System.out.println("Sorry, not event agents can withdraw more than $999,999.99 at once");
				else
					list.add(new Transaction(TransactionCodes.XFR, accountNumber1, amount, accountNumber2, "***"));
			}
		}
		
	}

	}
}