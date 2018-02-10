import java.util.StringJoiner;

public class Sudoku {
	private int empty=0;
	private int p=0;
	private int q=0;
	private int p1=0;
	private int q1=0;
	private int[][] matrix;
	private boolean[][][] m;
	public Sudoku(int[][] newMatrix,boolean[][][] booleanMatrix) {
		matrix=newMatrix;
		m=booleanMatrix;
	}
	private void updateBooleanMatrixNinebyNine(int existingNum,int rowIndex,int columnIndex) {
		for(int k=0;k<9;k++) {
			if(k!=(existingNum-1)) {  //Except for Block Number index in Boolean Matrix
				m[rowIndex][columnIndex][k]=false; // Update Block Number index to false
			}
			if(k!=columnIndex) { //Except for current column index in Boolean Matrix 
				m[rowIndex][k][existingNum-1]=false; // Update Block Number index to false
			}
			if(k!=rowIndex) { //Except for current row index in Boolean Matrix
				m[k][columnIndex][existingNum-1]=false;  // Update Block Number index to false
			}
		}
	}
	private void checkAvailableMatrixNumberNineByNine() {
		int block=0;
		for(int i=0;i<9;i++) {
			for(int j=0;j<9;j++) {
				block=matrix[i][j];
				if(block!=empty) {
					updateBooleanMatrixNinebyNine(block,i,j);
				}
			}
		}
	}
	public void updateBooleanMatrixThreeByThree(int rowIndex,int columnIndex,int comparableRowIndex,int comparableColumnIndex,int blockNum) {
		for(int x=rowIndex;x<rowIndex+3;x++) {
			for(int y=columnIndex;y<columnIndex+3;y++) {
				if(matrix[x][y]==empty) {
					if(!(x==comparableRowIndex&&y==comparableColumnIndex)){ // Except for compared block indices
						m[x][y][blockNum-1]=false;   //  Update block number to false in Boolean Matrix 
					}
				}
			}
		}
	}
	public void checkAvailableMatrixNumberThreeByThree() {
		int rowIndex=0;
		int columnIndex=0;
		int block=0;
		while(columnIndex<7) {
			while(rowIndex<7) {
				for(int i=rowIndex;i<rowIndex+3;i++) {
					for(int j=columnIndex;j<columnIndex+3;j++) {
						block=matrix[i][j];
						if(block!=empty) {
							updateBooleanMatrixThreeByThree(rowIndex,columnIndex,i,j,block);
						}
					}
				}
				rowIndex=rowIndex+3;
			}
			rowIndex=0;
			columnIndex=columnIndex+3;
		}
	}
	public void checkEachBlockContainSingleNum() {
		int count=0;
		int block=0;
		for(int i=0;i<9;i++) {
			for(int j=0;j<9;j++) {
				for(int k=0;k<9;k++) {
						if(m[i][j][k]==true) {
							block=k+1;
							count=count+1;
						}
						if(count>2) {
							break;
						}
					}
					if(count==1) {
						matrix[i][j]=block;
					}
					block=0;
					count=0;
				}
			}
	}
	public void checkOnePossibilityEachNumThreeByThree() {
		int rowIndex=0;
		int columnIndex=0;
		int count=0;
		int number=1;
		int currentRowIndex=0;
		int currentColumnIndex=0;
		while(columnIndex<7) {
			while(rowIndex<7) {
			    while(number<10) {
			    	for(int i=rowIndex;i<rowIndex+3;i++) {
						for(int j=columnIndex;j<columnIndex+3;j++) {
							if(m[i][j][number-1]==true) {
								count=count+1;
								currentRowIndex=i;currentColumnIndex=j;
							}
						}
					}
					if(count==1) {
						matrix[currentRowIndex][currentColumnIndex]=number;
					}
					currentRowIndex=0;
					currentColumnIndex=0;
					count=0;
					number++;
			    }
			    number=1;
			    rowIndex=rowIndex+3;
			}
			number=1;
			rowIndex=0;
			columnIndex=columnIndex+3;
		}
	}
	public void updateBooleanMatrixNumFoundTwiceThreebyThree(int blockNumber,int previousRowIndex,int previousColumnIndex,int currentRowIndex,int currentColumnIndex) {
		if(previousRowIndex==currentRowIndex) {
			for(int k=0;k<9;k++) {
				if((k!=previousColumnIndex)&&(k!=currentColumnIndex)) { // Except for column indices for the block number found twice
					m[previousRowIndex][k][blockNumber-1]=false;  // Update Boolean Matrix for the row of block number index to false
				}
			}
		}
		if(previousColumnIndex==currentColumnIndex) {
			for(int k=0;k<9;k++) {
				if((k!=previousRowIndex)&&(k!=currentRowIndex)) { // Except for row indices for the block number found twice
					m[k][previousColumnIndex][blockNumber-1]=false;  // Update Boolean Matrix for the column of block number index to false
				}
			}
		}
	}
	public void checkBooleanMatrixNumFoundTwiceThreebyThree() {
		int rowIndex=0;
		int columnIndex=0;
		int count=0;
		int number=1;
		while(columnIndex<7) {
			while(rowIndex<7) {
			    while(number<10) {
			    	boolean subBlockNumberFound=false,subBlockNumberFoundTwice=false;
			    	for(int i=rowIndex;i<rowIndex+3;i++) {
						for(int j=columnIndex;j<columnIndex+3;j++) {
							int block=matrix[i][j];
							if(block==empty) {
								boolean subBlockNumber=m[i][j][number-1];
								if(subBlockNumber==true) {
									 subBlockNumberFound=true;
									 count=count+1;
									 if(count>1) {
										 if(i==p) { // If current row index equals previous row index
											 subBlockNumberFoundTwice=true;
											 p1=i;q1=j;
										 }
										 if(j==q) { // If current column index equals previous column index
											 subBlockNumberFoundTwice=true;
											 p1=i;q1=j;
										 }
									}
								}
								if(count==1) {
									if(subBlockNumberFound==true) {
									p=i;q=j;
									}
									subBlockNumberFound=false;
								}
							}
						}
					}
					if(count==2) {
						if(subBlockNumberFoundTwice==true)	{
							updateBooleanMatrixNumFoundTwiceThreebyThree(number,p,q,p1,q1);
						}	
					}
					p=0;
					q=0;
					p1=0;
					q1=0;
					count=0;
					number++;
			    }
			    number=1;
			    rowIndex=rowIndex+3;
			}
			number=1;
			rowIndex=0;
			columnIndex=columnIndex+3;
		}
	}
	public int checkNumEmptyBlocks() {
		int count=0;
		for(int i=0;i<9;i++) {
			for(int j=0;j<9;j++) {
				if(matrix[i][j]==0) {
					count=count+1;
					break;
				}
			}
			if(count>1) {
				break;
			}
		}
		return count;
	}
	public int[][] returnMatrix(int[][] matrix){
		return matrix;
	}
	public void checkAvailableMatrixNumber(){
		checkAvailableMatrixNumberNineByNine();
		checkAvailableMatrixNumberThreeByThree();
	}
	public int getMatrix() {
		String lineSplit = "";
        StringJoiner sk = new StringJoiner("+", "|", "|");
        for (int i=0; i< matrix[0].length; i++) {
            sk.add(String.format("%3s", "").replace(" ", "-"));
        }
        lineSplit = sk.toString();
        for (int[] row : matrix) {
            StringJoiner sj = new StringJoiner(" | ", "| ", " |");
            for (int col : row) {
                sj.add(String.format("%d", col));
            }
            System.out.println(lineSplit);
            System.out.println(sj.toString());
        }
        System.out.println(lineSplit);
        int c=-1;
        return c;
	}
	public int repeatMethodsEmptyBlock() {
		int count=0;
		int[][] mt=new int[9][9];
		checkAvailableMatrixNumber();
		checkEachBlockContainSingleNum();
		checkAvailableMatrixNumber();
		checkOnePossibilityEachNumThreeByThree();
		checkAvailableMatrixNumber();
		checkBooleanMatrixNumFoundTwiceThreebyThree();
		checkAvailableMatrixNumber();
		count=checkNumEmptyBlocks();
		while(count>0) {
			if(mt==returnMatrix(matrix)) {
				System.out.println("The Game has multiple solutions!");
				break;
			}
			mt=matrix;
			count=repeatMethodsEmptyBlock();
		}
		if(count==0) {
			count=getMatrix();
		}
		return count;
	}
	public static void main(String[] args) {
		boolean[][][] m=new boolean[9][9][9];
		for(int a=0;a<9;a++ ) {
			for(int b=0;b<9;b++) {
				for(int c=0;c<9;c++) {
					m[a][b][c]=true;
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
		  	
		Sudoku sk=new Sudoku(matrixValues,m);
		sk.repeatMethodsEmptyBlock();
	}

}
