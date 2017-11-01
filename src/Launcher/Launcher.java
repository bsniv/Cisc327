package Launcher;
import BL.BL;
import DAL.Data;
import PL.*;

public class Launcher {
	public static void main(String[] args) {
		if (args.length == 2){
			Data.setTransactionFilePath(args[1]);
			Data.setUserFilePath(args[0]);
		}
		else {
			Data.setTransactionFilePath(null);
			Data.setUserFilePath(null);
		}
		ATM pl = new ATM();
		pl.start(new BL(new Data()));
	}
}
