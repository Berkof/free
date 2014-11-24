package sudtest;

import java.util.Arrays;

public class BlockValidator implements Runnable {

	private int blockIdx;
	private Sudoku sudoku;
	
	public BlockValidator(Sudoku sudoku, int blockIdx) {
		this.sudoku = sudoku;
		this.blockIdx = blockIdx;
		
	}

	@Override
	public void run() {
		int[] block = new int[Sudoku.sSize];
		int tIdx = 0;
		int blockStartRow = blockIdx / Sudoku.sBlockSize;
		int blockStartCol = blockIdx % Sudoku.sBlockSize;
		for (int i=0;i<Sudoku.sBlockSize;i++) {
			for (int j=0;j<Sudoku.sBlockSize;j++) {
				block[tIdx++] = sudoku.sudoku[blockStartRow + i][blockStartCol + j];
			}
		}
		
		Arrays.sort(block);
		for (int i=0;i<Sudoku.sSize;i++) {
			if (block[i] != Sudoku.allSymbols[i]) {
				sudoku.blockError(blockIdx, i / 3, i % 3); // Not quite good, but some value we have
				break;
			}
		}
	}
	
}
