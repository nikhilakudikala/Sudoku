
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.opentest4j.AssertionFailedError;

import static org.junit.Assert.*;

import java.util.Arrays;

import static org.junit.Assert.assertEquals;

class SudokuTest {
	@Test
	public void test()  {
		boolean[][][] booleanMatrix=new boolean[9][9][9];
		for(int a=0;a<9;a++ ) {
			for(int b=0;b<9;b++) {
				for(int c=0;c<9;c++) {
					booleanMatrix[a][b][c]=true;
				}
			}
		}
		int[][] matrixValues= {
				{0,7,5,4,0,0,0,0,1},
	            {0,3,2,0,0,0,0,9,5},
	            {0,0,0,1,2,5,0,8,0},
	            {5,0,0,2,3,0,0,0,4},
	            {2,0,8,5,1,0,0,0,0},
	            {4,1,0,0,0,8,6,5,2},
	            {3,0,0,0,5,0,9,2,0},
	            {9,0,6,0,4,0,0,3,0},
	            {0,0,0,3,0,9,5,0,8}
	            };
		int[][] matrixValues1={
				{0,9,2,0,7,0,0,0,0},
				{5,7,0,1,0,8,0,3,9},
				{0,0,8,2,0,5,4,0,7},
				{0,0,0,0,0,6,8,0,0},
				{8,6,0,0,0,0,0,7,2},
				{0,0,4,3,0,0,0,0,0},
				{2,0,9,5,0,7,6,0,0},
				{3,4,0,8,0,9,0,2,1},
				{0,0,0,0,1,0,7,9,0}
				};
		int[][] expectedMatrixValues= {
		{8,7,5,4,9,3,2,6,1},
        {1,3,2,6,8,7,4,9,5},
        {6,4,9,1,2,5,7,8,3},
        {5,9,7,2,3,6,8,1,4},
        {2,6,8,5,1,4,3,7,9},
        {4,1,3,9,7,8,6,5,2},
        {3,8,4,7,5,1,9,2,6},
        {9,5,6,8,4,2,1,3,7},
        {7,2,1,3,6,9,5,4,8}
        };
		int[][] expectedMatrixValues1={
				{4,9,2,6,7,3,1,8,5},
				{5,7,6,1,4,8,2,3,9},
				{1,3,8,2,9,5,4,6,7},
				{9,5,3,7,2,6,8,1,4},
				{8,6,1,9,5,4,3,7,2},
				{7,2,4,3,8,1,9,5,6},
				{2,1,9,5,3,7,6,4,8},
				{3,4,7,8,6,9,5,2,1},
				{6,8,5,4,1,2,7,9,3}
				};
		Sudoku sk=new Sudoku(matrixValues,booleanMatrix);
		sk.repeatMethodsEmptyBlock();
	    Assertions.assertArrayEquals(expectedMatrixValues,sk.returnMatrix(matrixValues));
	    Assertions.assertArrayEquals(expectedMatrixValues1,sk.returnMatrix(matrixValues1));
	}
}
