package SharedClasses;

public class Transaction {

	String MMM;
	String NNN;
	//add other attributes
	
	
	public Transaction(String type, String NNN) {
		MMM = type;
		this.NNN = NNN;
	}

	public String toString(){
		return MMM + " " + NNN;
	}
}
