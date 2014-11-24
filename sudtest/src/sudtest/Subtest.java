package sudtest;

import java.util.ArrayList;
import java.util.List;


public class Subtest {

	public static void main(String argv[]) throws InterruptedException {
		if (81 != argv.length) {
			System.out.println("Use sudtest <81 parameters = sudoku line by line>");
			System.exit(1);
		}
		int tparam = 0;
		Sudoku s = new Sudoku();
		for (int i=0;i<Sudoku.sSize;i++) {
			for (int j=0;j<Sudoku.sSize;j++) {
				s.sudoku[i][j] = Integer.valueOf(argv[tparam++]);
			}
		}
		// Start test threads
		List<Thread> threads = new ArrayList<Thread>(Sudoku.sSize * 3);
		// Col
		for (int i=0;i<Sudoku.sSize;i++) {
			ColumnValidator tColVal = new ColumnValidator(s, i);
			Thread tThread = new Thread(tColVal);
			threads.add(tThread);
			tThread.start();
		}
		// Row
		for (int i=0;i<Sudoku.sSize;i++) {
			RowValidator tRowVal = new RowValidator(s, i);
			Thread tThread = new Thread(tRowVal);
			threads.add(tThread);
			tThread.start();
		}
		// Block
		for (int i=0;i<Sudoku.sSize;i++) {
			BlockValidator tBVal = new BlockValidator(s, i);
			Thread tThread = new Thread(tBVal);
			threads.add(tThread);
			tThread.start();
		}
		for (Thread tThread : threads) {
			tThread.join();
		}
		// Test answer
		// Col
		for (int i=0;i<Sudoku.sSize;i++) {
			if (Sudoku.ERROR_CODE == s.colTest[i]) {
				System.out.println("Sudoku is invalid: column " + s.errCol + getErrorBlockStr(s));
				System.exit(0);
			}
		}
		// Row
		for (int i=0;i<Sudoku.sSize;i++) {
			if (Sudoku.ERROR_CODE == s.rowTest[i]) {
				System.out.println("Sudoku is invalid: row " + s.errRow + getErrorBlockStr(s));
				System.exit(1);
			}
		}
		
		// Block
		for (int i=0;i<Sudoku.sSize;i++) {
			if (Sudoku.ERROR_CODE == s.rowTest[i]) {
				System.out.println("Sudoku is invalid: " + getErrorBlockStr(s));
				System.exit(1);
			}
		}
		System.out.println("Sudoku is valid");
	}
	
	private static String getErrorBlockStr(Sudoku s) {
		 return " on block " + s.getErrorBlock() + " :" + s.getErrorRowInBlock() + "/" + s.getErrorColInBlock();
	}
}
