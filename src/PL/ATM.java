package PL;


import java.util.Scanner;
import BL.BL;
import SharedClasses.Transaction;
import SharedClasses.Transaction.TransactionCodes;

public class ATM 
{

	BL bl;
	Scanner s;
	boolean loggedIn; //Is the user logged in?
	boolean isAgent; //Is the user an agent?
	boolean sessionTypeChosen; //Has the user chosen a mode yet?
	
	public ATM()
	{
		s = new Scanner(System.in);
		loggedIn = false;
		isAgent = false;
	}
	
	public void start(BL bl) 
	{
		this.bl = bl;
		
		while (true)
		{
			while (!loggedIn) //wait until the user logins before accepting any other input
				loggedIn = login();
			
			while (!sessionTypeChosen) //wait until the user decides to choose agent or ATM mode
				sessionTypeChosen = chooseSessionType();
			
			while (loggedIn)
			{
				outputCommands(isAgent);
				
				executeCommand (s.nextLine());
			}
		}
	}

	private boolean login()
	{
		System.out.println("Please login:");
		String input = s.nextLine();
		if (input.equals("login"))
			return true;
		else
			return false;
	}
	
	private boolean chooseSessionType()
	{
		System.out.println("Enter 'machine' for a normal session, or 'agent' for an agent session:");
		String input = s.nextLine();
		if (input.equals("machine"))
		{
			isAgent = false;
			return true;
		}
		else if  (input.equals("agent"))
		{
			isAgent = true;
			return true;
		}
		else
			return false;
	}
	
	private void outputCommands(boolean isAgent)
	{
		if (isAgent)
			System.out.print("Please enter a command from the following list:\nlogout\ncreateacct *account-number* *account-name*\ndeleteacct *account-number* *account-name*\ndeposit *account-number* *amount-in-cents*\nwithdraw *account-number* *amount-in-cents*\ntransfer *from-account-number* *to-account-number* *amount-in-cents*\n");
		else
			System.out.print("Please enter a command from the following list:\nlogout\ndeposit *account-number* *amount-in-cents*\nwithdraw *account-number* *amount-in-cents*\ntransfer *from-account-number* *to-account-number* *amount-in-cents*\n");
	}
	
	private void executeCommand(String command)
	{
		String[] parts = command.split(" ");
		
		switch (parts[0])
		{
		case "logout":     logout();
					       break;
		case "createacct": createacct(command);
						   break;
		case "deleteacct": deleteacct(command);
						   break;
		case "deposit":    deposit(command);
						   break;
		case "withdraw":   withdraw(command);
						   break;
		case "transfer":   transfer(command);
						   break;
		default: 		   System.out.println("That is an invalid input. Please try again.");
		}
	}
		
	public void logout() 
	{
		loggedIn = false;
		isAgent = false;
		sessionTypeChosen = false;
		
		bl.addTransaction(new Transaction(TransactionCodes.EOS, "000", "000", "000", "***"));
		bl.writeTransactions();	
		System.out.println("You have successfully logged out. Have a nice day.");
	}
	
	public boolean validAccountNumber(String number) //Checks if given account number is valid
	{
		if (number.length() != 7) //If the account number is not exactly 7 digits, it is invalid
		{
			System.out.println("That account number is not 7 digits long. Please try again");
			return false;
		}
		
		for (int i = 0; i < number.length(); i++) //If the account number is not comprised solely of digits, it is invalid
		{
			if (!Character.isDigit(number.charAt(i)))
			{
				System.out.println("The account number may only be comprised of digits 0-9, and may not include a leading 0. Please try again");
				return false;
			}
		}
		
		if (number.charAt(0) == '0') //If the account number begins with a '0', it is invalid
		{
			System.out.println("Account numbers cannot begin with a '0'. Please try again.");
			return false;
		}
		
		return true;
	}
	
	public boolean validAccountName(String name) //Checks if given account name is valid
	{
		if (name.length() > 30 || name.length() <3) //If the account name is not between 3 and 30 characters, it is invalid
		{
			System.out.println("Account names must be between 3 and 30 alphanumeric characters. Please try again.");
			return false;
		}
		
		for (int i = 0; i < name.length(); i++) //If the account name contains characters other than letters, numbers, and spaces, it is invalid
		{
			if (!Character.isAlphabetic(name.charAt(i)) && !(name.charAt(i) == ' ') && !Character.isDigit(name.charAt(i)))
			{
				System.out.println("Account names can only contain alphanumeric characters and spaces. Please try again.");
				return false;
			}
		}
		
		if (name.charAt(0) == ' ')
		{
			System.out.println("Account names cannot begin with a space. Please try again.");
			return false;
		}
		
		if (name.charAt(name.length()-1) == ' ')
		{
			System.out.println("Account names cannot end with a space. Please try again.");
			return false;
		}
			
		return true;
	}
	
	public boolean validMoneyAmount(String money) //Checks that the given money string is valid
	{
		for (int i = 0; i < money.length(); i++) //If the string contains any characters other than digits, it is invalid
		{
			if (!Character.isDigit(money.charAt(i)))
			{
				System.out.println("Money amounts can only contain digits. Please try again.");
				return false;
			}
		}
		
		if (money.charAt(0) == '0') //If the string begins with a 0, it is invalid
		{
			System.out.println("Money amounts cannot begin with '0'. Please try again.");
			return false;
		}
		
		int amount = Integer.parseInt(money);
		if (!isAgent && amount > 100000) //if a non-agent attempts to move more than $1000, it is invalid
		{
			System.out.println("Sorry, only agents can move amounts greater than $1000. Please try again.");
			return false;
		}
		else if (isAgent && amount > 99999999)
		{
			System.out.println("Sorry, not even agents can move amounts greater than $999,999.99. Please try again.");
			return false;
		}
		
		return true;
	}
	
	private void createacct(String command)
	{
		if (!isAgent) //Do not allow unprivileged users access
		{
			System.out.println("Sorry, that is a privileged command. Please try again.");
			return;
		}
			
		String[] parts = command.split(" "); //If there are more than 3 arguments, it is invalid
		if (parts.length != 3)
		{
			System.out.println("That is an invalid input. Please try again.");
			return;
		}
		
		if (!validAccountNumber(parts[1])) //Check that the account number is valid
			return;
		
		if (!bl.validateFreeAccountNumber(parts[1])) //Check that the account number is not already in use
		{
			System.out.println("That account number is already taken. Please try again.");
			return;
		}
		
		if (!validAccountName(parts[2])) //Check that the account name is valid
			return;
			
		System.out.println("You have successfully created an account with Account Number: " + parts[1] + " and Account Name: " + parts[2] + ".");
		bl.addTransaction(new Transaction(TransactionCodes.NEW, parts[1], "000", "000", parts[2]));	
	}
	
	private void deleteacct(String command)
	{
		if (!isAgent)
		{
			System.out.println("Sorry, that is a privileged command. Please try again.");
			return;
		}
			
		String[] parts = command.split(" ");
		if (parts.length != 3)
		{
			System.out.println("That is an invalid input. Please try again.");
			return;
		}
		
		if (!validAccountNumber(parts[1])) //Check that the account number is valid
			return;
		
		if (bl.validateFreeAccountNumber(parts[1])) //Check that the account number is in use
		{
			System.out.println("That account number is not assigned to an account. Please try again.");
			return;
		}
		
		if (!validAccountName(parts[2])) //Check that the account name is valid
			return;
		
		System.out.println("You have successfully deleted the account with Account Number: " + parts[1] + " and Account Name: " + parts[2] + ".");
		bl.addTransaction(new Transaction(TransactionCodes.DEL, parts[1], "000", "000", parts[2]));
	}
	
	private void deposit(String command)
	{
		String[] parts = command.split(" ");
		if (parts.length != 3)
		{
			System.out.println("That is an invalid input. Please try again.");
			return;
		}
		
		if (!validAccountNumber(parts[1])) //Check that the account number is valid
			return;
		
		if (bl.validateFreeAccountNumber(parts[1])) //Check that the account number is in use
		{
			System.out.println("That account number is not assigned to an account. Please try again.");
			return;
		}
		
		if (!validMoneyAmount(parts[2])) //Check that the money value is valid
			return;
		
		System.out.println("You have successfully deposited $" + Double.parseDouble(parts[2])/100 + " to Account Number: " + parts[1] + ".");
		bl.addTransaction(new Transaction(TransactionCodes.DEP, parts[1], parts[2], "000", "***"));	
	}
	
	private void withdraw(String command)
	{
		String[] parts = command.split(" ");
		if (parts.length != 3)
		{
			System.out.println("That is an invalid input. Please try again.");
			return;
		}
		
		if (!validAccountNumber(parts[1])) //Check that the account number is valid
			return;
		
		if (bl.validateFreeAccountNumber(parts[1])) //Check that the account number is in use
		{
			System.out.println("That account number is not assigned to an account. Please try again.");
			return;
		}
		
		if (!validMoneyAmount(parts[2])) //Check that the money value is valid
			return;
		
		System.out.println("You have successfully withdrawn $" + Double.parseDouble(parts[2])/100 + " from Account Number: " + parts[1] + ".");
		bl.addTransaction(new Transaction(TransactionCodes.WDR, parts[1], parts[2], "000", "***"));
	}
	
	private void transfer(String command)
	{
		String[] parts = command.split(" ");
		if (parts.length != 4)
		{
			System.out.println("That is an invalid input. Please try again.");
			return;
		}
		
		if (!validAccountNumber(parts[1])) //Check that the first account number is valid
			return;
		
		if (!validAccountNumber(parts[2])) //Check that the second account number is valid
			return;
		
		if (bl.validateFreeAccountNumber(parts[1])) //Check that the first account number is in use
		{
			System.out.println("The first account number is not assigned to an account. Please try again.");
			return;
		}
		
		if (bl.validateFreeAccountNumber(parts[2])) //Check that the second account number is in use
		{
			System.out.println("The second account number is not assigned to an account. Please try again.");
			return;
		}
		
		if (!validMoneyAmount(parts[3])) //Check that the money amount is valid
			return;
		
		System.out.println("You have successfully transferred $" + Double.parseDouble(parts[3])/100 + " from Account Number: " + parts[1] + " to Account Number: " + parts[2] + ".");
		bl.addTransaction(new Transaction(TransactionCodes.XFR, parts[1], parts[3], parts[2], "***"));
	}
}