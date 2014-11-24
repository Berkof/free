package sudtest;

import java.util.Arrays;

public class RowValidator implements Runnable {

	private int rowIdx;
	private Sudoku sudoku;
	
	public RowValidator(Sudoku sudoku, int rowIdx) {
		this.sudoku = sudoku;
		this.rowIdx = rowIdx;
	}

	@Override
	public void run() {
		int[] row = new int[Sudoku.sSize];
		System.arraycopy(sudoku.sudoku[rowIdx], 0, row, 0, Sudoku.sSize);
		Arrays.sort(row);
		for (int i=0;i<Sudoku.sSize;i++) {
			if (row[i] != Sudoku.allSymbols[i]) {
				sudoku.rowError(i, rowIdx);
				break;
			}
		}
	}
}
