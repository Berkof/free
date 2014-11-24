package sudtest;

public class Sudoku {

	public int[][] sudoku;
	public int[] colTest;
	public int[] rowTest;
	public int[] blockTest;
	public int errCol;
	public int errRow;
	
	public static final int[] allSymbols = {1,2,3,4,5,6,7,8,9};
	
	private static final int NO_ERR_IDX = -1;
	public static final int ERROR_CODE = 1;
	
	public static final int sBlockSize = 3;
	public static final int sSize = sBlockSize * sBlockSize;
	
	public synchronized void colError(int colIdx, int rowIdx) {
		if (NO_ERR_IDX == errCol) {
			errCol = colIdx;
			errRow = rowIdx;
			colTest[colIdx] = ERROR_CODE;
		}
	}
	
	
	public synchronized void rowError(int colIdx, int rowIdx) {
		if (NO_ERR_IDX == errCol) {
			errCol = colIdx;
			errRow = rowIdx;
			rowTest[rowIdx] = ERROR_CODE;
		}
	}
	
	
	public synchronized void blockError(int blockIdx, int colIdx, int rowIdx) {
		if (NO_ERR_IDX == errCol) {
			int blockRow = (blockIdx / sBlockSize) * sBlockSize;
			int blockCol = (blockIdx % sBlockSize) * sBlockSize;
			errCol = colIdx + blockCol;
			errRow = rowIdx + blockRow;
			blockTest[blockIdx] = ERROR_CODE;
		}
	}
	
	public int getErrorBlock() {
		return (errRow / sBlockSize) * sBlockSize + (errCol / sBlockSize);
	}
	
	public int getErrorColInBlock() {
		return errRow % sBlockSize;
	}
	
	public int getErrorRowInBlock() {
		return errCol % sBlockSize;
	}
	
	public Sudoku() {
		sudoku = new int[sSize][];
		for (int i=0;i<sSize;i++) {
			sudoku[i] = new int[sSize];
		}
		errCol = NO_ERR_IDX;
		errRow = NO_ERR_IDX;
		colTest = new int[sSize];
		rowTest = new int[sSize];
		blockTest = new int[sSize];
		
	}
}
