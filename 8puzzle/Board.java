import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import edu.princeton.cs.algs4.StdRandom;

public class Board {
	private final int n; //Dimension of the board
	private final int[][] board;
	private int x, y; //Coordinates used for finding where 0 is present

	public Board(int[][] blocks)           // construct a board from an n-by-n array of blocks
	// (where blocks[i][j] = block in row i, column j)
	{
		this.n = blocks[0].length;
		this.board = new int[n][n];
		for(int i=0; i<n; i++)
		{
			for(int j=0; j<n; j++)
			{
				this.board[i][j] = blocks[i][j];
			}
		}
	}
	
	public int dimension()                 // board dimension n
	{
		return n;
	}
	public int hamming()                   // number of blocks out of place
	{
//		System.out.println("Hamming counter: "+(hammingCounter++));
		int counter = 0;
		int[][] goalBoard = new int[n][n];
		
		for(int i=0; i<n; i++)
		{
			for(int j=0; j<n; j++)
			{
				goalBoard[i][j] = 1 + n*i + j;
			}
		}
		
		goalBoard[n-1][n-1] = 0;
		
		for(int i=0; i<n; i++)
		{
			for(int j=0; j<n; j++)
			{
				if((board[i][j] != goalBoard[i][j]) && (board[i][j]!=0))
				{
					counter++;
				}
			}
		}
		return counter;
	}
	public int manhattan()                 // sum of Manhattan distances between blocks and goal
	{
		//System.out.println("Manhattan counter: "+(hammingCounter++));
		int counter = 0;
		int a, b;
		
		for(int i=0; i<n; i++)
		{
			for(int j=0; j<n; j++)
			{
				if((board[i][j] != (1 + n*i + j)) && (board[i][j]!=0))
				{
					if(board[i][j]%n == 0)
					{
						a = board[i][j]/n-1;
						b = Math.max(0, n-1);
//						System.out.println("In if: i="+i+" j="+j+" a="+a+" b="+b);
					}
					else
					{
						a = Math.max(0, (board[i][j]/n));
						b = Math.max(0, (board[i][j]%n)-1);
						//System.out.println("In else: i="+i+" j="+j+" a="+a+" b="+b);
					}
					counter = counter+ Math.abs(a-i) + Math.abs(b-j);
				}
			}
		}
		
		return counter;
	}
	
	public boolean isGoal()                // is this board the goal board?
	{
		int[][] goalBoard = new int[n][n];
		
		for(int i=0; i<n; i++)
		{
			for(int j=0; j<n; j++)
			{
				goalBoard[i][j] = 1 + n*i + j;
			}
		}
		
		goalBoard[n-1][n-1] = 0;
		for(int i=0; i<n; i++)
		{
			for(int j=0; j<n; j++)
			{
				if(board[i][j] != goalBoard[i][j])
					return false;
			}
		}
		
		return true;
		
	}
	
	public Board twin()                    // a board that is obtained by exchanging any pair of blocks
	{
		int a, b, c, d, temp;
		Board twinBoard = new Board(board);
		do{
			a = StdRandom.uniform(n);
			b = StdRandom.uniform(n);
			c = StdRandom.uniform(n);
			d = StdRandom.uniform(n);
		}while((twinBoard.board[a][b]==0) || (twinBoard.board[c][d]==0) || (twinBoard.board[a][b] == twinBoard.board[c][d]));
//		System.out.println(a+" "+b+" "+c+" "+d);
		
		temp = twinBoard.board[a][b];
		twinBoard.board[a][b] = twinBoard.board[c][d];
		twinBoard.board[c][d] = temp;
		
		return twinBoard;
	}
	public boolean equals(Object y)        // does this board equal y?
	{
		if (y == this) return true;
		if (y == null) return false;
		if (y.getClass() != this.getClass()) return false;
		Board that = (Board) y;
		if(that.board.length != this.board.length)
			return false;
		for(int i=0; i<n; i++)
			for(int j=0; j<n; j++)
				if(this.board[i][j] != that.board[i][j])
					return false;
		return true;
	}
	
	private void searchZero(int [][]blocks)
	{
		for(int i=0; i<n; i++)
			for(int j=0; j<n; j++)
			{
				if(blocks[i][j] == 0)
				{
					x=i;
					y=j;
					break;
				}
			}
		
	}
	
	private int[][] deepCopy()
	{
		int[][] arr = new int[n][n];
		for(int i=0; i<n; i++)
		{
			for(int j=0; j<n; j++)
			{
				arr[i][j] = board[i][j];
			}
		}
		return arr;
	}
	public Iterable<Board> neighbors()     // all neighboring boards
	{
		ArrayList<Board> neighborsList = new ArrayList<Board>();
		int [][] tempBlocks = new int[n][n];
		searchZero(this.board);
		for(int i=0; i<4; i++) //i=0 -> left, i=1->top, i=2->right, i=3->bottom 
		{
			if((i == 0) && (y>0))
			{
				tempBlocks = deepCopy();
				tempBlocks[x][y] = tempBlocks[x][y-1];
				tempBlocks[x][y-1] = 0;
				Board tempBoard = new Board(tempBlocks);
				neighborsList.add(tempBoard);
			}
			else if((i == 1) && (x>0))
			{
				tempBlocks = deepCopy();
				tempBlocks[x][y] = tempBlocks[x-1][y];
				tempBlocks[x-1][y] = 0;
				Board tempBoard = new Board(tempBlocks);
				neighborsList.add(tempBoard);
			}
			else if((i == 2) && (y<n-1))
			{
				tempBlocks = deepCopy();
				tempBlocks[x][y] = tempBlocks[x][y+1];
				tempBlocks[x][y+1] = 0;
				Board tempBoard = new Board(tempBlocks);
				neighborsList.add(tempBoard);
			}
			else if((i == 3) && (x<n-1))
			{
				tempBlocks = deepCopy();
				tempBlocks[x][y] = tempBlocks[x+1][y];
				tempBlocks[x+1][y] = 0;
				Board tempBoard = new Board(tempBlocks);
				neighborsList.add(tempBoard);
			}

		}


		return neighborsList;
	}

	
	public String toString()               // string representation of this board (in the output format specified below)
	{
	    StringBuilder s = new StringBuilder();
	    s.append(n + "\n");
	    for (int i = 0; i < n; i++) {
	        for (int j = 0; j < n; j++) {
	            s.append(String.format("%2d ", this.board[i][j]));
	        }
	        s.append("\n");
	    }
	    return s.toString();
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		List<Integer> randList = new ArrayList<>();
		int dim = 3;
		
		for(int i=0; i<dim; i++)
		{
			for(int j=0; j<dim; j++)
			{
				randList.add(dim*i + j);
			}
		}
		
		Collections.shuffle(randList);
		
		int [][] blockArg = new int[dim][dim];
		for(int i=0; i<dim; i++)
		{
			for(int j=0; j<dim; j++)
			{
				blockArg[i][j] = randList.remove(dim*dim-1-(dim*i+j));
			}
		}

		blockArg[0][0] = 5;
		blockArg[0][1] = 0;
		blockArg[0][2] = 4;
		blockArg[1][0] = 2;
		blockArg[1][1] = 3;
		blockArg[1][2] = 8;
		blockArg[2][0] = 7;
		blockArg[2][1] = 1;
		blockArg[2][2] = 6;
		
		
		Board boardObj = new Board(blockArg);
		ArrayList<Board> neighborsListObj = new ArrayList<Board>();
		
//		System.out.println(boardObj.toString());
//		System.out.println(boardObj.twin().toString());
		boardObj.searchZero(blockArg);
		//System.out.println("x="+boardObj.x + " y="+boardObj.y);
		
//		System.out.println("Hamming Distance = "+boardObj.hamming());
//		System.out.println("Manhattan Distance = "+boardObj.manhattan());
		neighborsListObj = (ArrayList<Board>)boardObj.neighbors();
		for(Board b: neighborsListObj)
		{
//			System.out.println(b.toString());
		}
	}

}
