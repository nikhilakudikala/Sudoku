import java.util.StringJoiner;

public class Sudoku {
	private int number=1;
	private int empty=0;
	private int block=0;
	private int count=0;
	private int e=0;
	private int f=0;
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
	//comment
	private void delExistingNumRowColumnNinebyNine(int existingNum,int rowIndex,int columnIndex) {
		for(int k=0;k<9;k++) {
			if(k!=(existingNum-1)) {
				m[rowIndex][columnIndex][k]=false;
			}
			if(k!=columnIndex) {
				m[rowIndex][k][existingNum-1]=false;
			}
			if(k!=rowIndex) {
				m[k][columnIndex][existingNum-1]=false;
			}
		}
	}
	private void delExistingNumRowColumnNinebyNine() {
		for(int i=0;i<9;i++) {
			for(int j=0;j<9;j++) {
				block=matrix[i][j];
				if(block!=empty) {
					delExistingNumRowColumnNinebyNine(block,i,j);
				}
			}
		}
	}
	public void delExistingNumRowColumnThreeByThree(int rowIndex,int columnIndex,int existingNum) {
		for(int x=rowIndex;x<rowIndex+3;x++) {
			for(int y=columnIndex;y<columnIndex+3;y++) {
				if(matrix[x][y]==empty) {
					if(!(x==rowIndex&&y==columnIndex)){
						m[x][y][existingNum-1]=false;
					}
				}
			}
		}
	}
	public void delExistingNumRowColumnThreeByThree() {
		e=0;
		f=0;
		while(f<7) {
			while(e<7) {
				for(int i=e;i<e+3;i++) {
					for(int j=f;j<f+3;j++) {
						block=matrix[i][j];
						if(block!=empty) {
							delExistingNumRowColumnThreeByThree(e,f,block);
						}
					}
				}
				e=e+3;
			}
			e=0;
			f=f+3;
		}
	}
	public void checkEachBlockContainSingleNum() {
		count=0;
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
		e=0;
		f=0;
		count=0;
		while(f<7) {
			while(e<7) {
			    while(number<10) {
			    	for(int i=e;i<e+3;i++) {
						for(int j=f;j<f+3;j++) {
							if(m[i][j][number-1]==true) {
								count=count+1;
								p=i;q=j;
							}
						}
					}
					if(count==1) {
						matrix[p][q]=number;
					}
					p=0;
					q=0;
					count=0;
					number++;
			    }
			    number=1;
				e=e+3;
			}
			number=1;
			e=0;
			f=f+3;
		}
	}
	public void delExistingNumRowColumnFoundTwiceThreebyThree(int blockNumber,int previousRowIndex,int previousColumnIndex,int currentRowIndex,int currentColumnIndex) {
		if(previousRowIndex==currentRowIndex) {
			for(int k=0;k<9;k++) {
				if((k!=previousColumnIndex)&&(k!=currentColumnIndex)) {
					m[previousRowIndex][k][blockNumber-1]=false;
				}
			}
		}
		if(previousColumnIndex==currentColumnIndex) {
			for(int k=0;k<9;k++) {
				if((k!=previousRowIndex)&&(k!=currentRowIndex)) {
					m[k][previousColumnIndex][blockNumber-1]=false;
				}
			}
		}
	}
	public void delExistingNumRowColumnFoundTwiceThreebyThree() {
		e=0;
		f=0;
		count=0;
		while(f<7) {
			while(e<7) {
			    while(number<10) {
			    	boolean subBlockNumberFound=false,subBlockNumberFoundTwice=false;
					for(int i=e;i<e+3;i++) {
						for(int j=f;j<f+3;j++) {
							block=matrix[i][j];
							if(block==empty) {
								boolean subBlockNumber=m[i][j][number-1];
								if(subBlockNumber==true) {
									 subBlockNumberFound=true;
									 count=count+1;
									 if(count>1) {
										 if(i==p) {
											 subBlockNumberFoundTwice=true;
											 p1=i;q1=j;
										 }
										 if(j==q) {
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
							delExistingNumRowColumnFoundTwiceThreebyThree(number,p,q,p1,q1);
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
			    e=e+3;
			}
			number=1;
			e=0;
			f=f+3;
		}
	}
	public int checkNumEmptyBlocks() {
		count=0;
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
	public int getMatrix(int c) {
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
        c=-1;
        return c;
	}
	public void repeatMethodsEmptyBlock() {
	
		int[][] mt=new int[9][9];
		delExistingNumRowColumnNinebyNine();
		delExistingNumRowColumnThreeByThree();
		checkEachBlockContainSingleNum();
		delExistingNumRowColumnNinebyNine();
		delExistingNumRowColumnThreeByThree();
		checkOnePossibilityEachNumThreeByThree();
		delExistingNumRowColumnNinebyNine();
		delExistingNumRowColumnThreeByThree();
		delExistingNumRowColumnFoundTwiceThreebyThree();
		delExistingNumRowColumnNinebyNine();
		delExistingNumRowColumnThreeByThree();
		count=checkNumEmptyBlocks();
		while(count>0) {
			if(mt==returnMatrix(matrix)) {
				System.out.println("The Game has multiple solutions!");
				break;
			}
			mt=matrix;
			repeatMethodsEmptyBlock();
			
		}
		if(count==0) {
			count=getMatrix(count);
		}
		
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
