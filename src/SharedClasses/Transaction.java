package SharedClasses;


public class Transaction {

	public enum TransactionCodes {
		DEP, WDR, XFR, NEW, DEL, EOS
	}
	TransactionCodes CCC;
	String toAccountNumber;
	String amount;
	String fromAccountNumber;
	String accountName;
	
	
	//add other attributes
	
	
	public Transaction(TransactionCodes code, String toAccountNumber, String amount, String fromAccountNumber, String accountName) {
		CCC = code;
		this.toAccountNumber = toAccountNumber;
		this.amount = amount;
		this.fromAccountNumber = fromAccountNumber;
		this.accountName = accountName;
	}

	public String toString(){
		return  CCC.toString() + " " + toAccountNumber + " " + amount + " " + fromAccountNumber + " " + accountName;
	}
	
	public String getToAccountNumber(){
		return toAccountNumber;
	}
	
	public String getFromAccountNumber(){
		return fromAccountNumber;
	}
	
	public TransactionCodes getTC(){
		return CCC;
	}
}
