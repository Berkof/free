package sudtest;

import java.util.Arrays;

public class ColumnValidator implements Runnable {

	private int columnIdx;
	private Sudoku sudoku;
	
	public ColumnValidator(Sudoku sudoku, int columnIdx) {
		this.sudoku = sudoku;
		this.columnIdx = columnIdx;
	}
	
	@Override
	public void run() {
		int[] col = new int[Sudoku.sSize];
		for (int i=0;i<Sudoku.sSize;i++) {
			col[i] = sudoku.sudoku[i][columnIdx];
		}
		Arrays.sort(col);
		for (int i=0;i<Sudoku.sSize;i++) {
			if (col[i] != Sudoku.allSymbols[i]) {
				sudoku.colError(columnIdx, i);
				break;
			}
		}
	}
	
}
