package Launcher;
import BL.BL;
import DAL.Data;
import PL.*;

public class Launcher {
	public static void main(String[] args) {
		ATM pl = new ATM();
		pl.start(new BL(new Data()));
	}

}
