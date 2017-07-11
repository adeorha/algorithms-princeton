import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.SeparateChainingHashST;
import edu.princeton.cs.algs4.Stack;

public class Solver {
	
	private final Board initBoard;
	private final int n;
	private  boolean isSolvable = true;
	//private MinPQ<SearchNode> pqOrig;
	private Stack<SearchNode> revSolution;
	private	int moves;
	
	public Solver(Board initial)           // find a solution to the initial board (using the A* algorithm)
	{
		MinPQ<SearchNode> pqOrig;
		this.n = initial.dimension();
		this.initBoard = initial;
		int origIsGoal = 0;

		revSolution = new Stack<SearchNode>();

		SearchNode searchNodeOrig = new SearchNode();
		SearchNode searchNodeTwin = new SearchNode();
		
		searchNodeOrig.board = initial;
		searchNodeOrig.numMoves = 0;
		searchNodeOrig.prevNode = null;
		
		searchNodeTwin.board = initial.twin();
		searchNodeTwin.numMoves = 0;
		searchNodeTwin.prevNode = null;
		
		pqOrig = new MinPQ<SearchNode>();
		pqOrig.insert(searchNodeOrig);
		MinPQ<SearchNode> pqTwin = new MinPQ<SearchNode>();
		pqTwin.insert(searchNodeTwin);
//		System.out.println("Manhattan original: "+pqOrig.min().board.manhattan());
//		System.out.println("Hamming original: "+pqOrig.min().board.hamming());
		//System.out.println(pqOrig.min().board.toString());
		//Check if original board is already solved.
		if(pqOrig.min().board.isGoal() == true)
		{
			isSolvable = true;
			revSolution.push(pqOrig.min());
			origIsGoal = 1;
		}
		else if(pqTwin.min().board.isGoal() == true)
			isSolvable = false;
		while((pqOrig.min().board.isGoal()==false) && (pqTwin.min().board.isGoal()==false))
		{
			oneMove(pqOrig);
			oneMove(pqTwin);
			if(pqOrig.min().board.isGoal() == true)
			{
				isSolvable = true;
				revSolution.push(pqOrig.min());
//				oneMove(pqOrig);
			}
			else if(pqTwin.min().board.isGoal() == true)
			{
				isSolvable = false;
//				oneMove(pqTwin);
			}
		}
		
		if(isSolvable && origIsGoal!=1)
		{
			while(!revSolution.peek().prevNode.board.equals(initBoard))
			{
				revSolution.push(revSolution.peek().prevNode);
			}
			revSolution.push(revSolution.peek().prevNode);
		}
		
		moves = pqOrig.min().numMoves;
		
//		System.out.println("Final Board orig" + pqOrig.min().board.toString());
//		System.out.println("Final Board twin" + pqTwin.min().board.toString());
		
	}

	private void oneMove(MinPQ<SearchNode> pqObj)
	{
			//ArrayList<Board> neighborsBoardList = new ArrayList<Board>();
			SearchNode dequeuedNode = new SearchNode();
			dequeuedNode = pqObj.delMin();
		//	counter++;
//			System.out.println("Dequeued Node, counter="+counter+"\n"+dequeuedNode.board.toString());
			//System.out.println("Dequeued Node\n"+dequeuedNode.board.toString());
			//neighborsBoardList = (ArrayList<Board>) dequeuedNode.board.neighbors();
		
			for(Board b: dequeuedNode.board.neighbors())
			{
				SearchNode enqueuingNode = new SearchNode();
				enqueuingNode.board = b;
				enqueuingNode.prevNode = dequeuedNode;
				enqueuingNode.numMoves = dequeuedNode.numMoves+1;
				if((dequeuedNode.prevNode==null) || !b.equals(dequeuedNode.prevNode.board))
					pqObj.insert(enqueuingNode);
			}
		/*	
			while(neighborsBoardList.size() > 0)
			{
				SearchNode enqueuingNode = new SearchNode();
				enqueuingNode.board = neighborsBoardList.remove(0);
				enqueuingNode.prevNode = dequeuedNode;
				enqueuingNode.numMoves = dequeuedNode.numMoves+1;
				if(enqueuingNode != dequeuedNode.prevNode)
					pqObj.insert(enqueuingNode);
			//	System.out.println(enqueuingNode.numMoves);
//					System.out.println("neigbor: \n" +enqueuingNode.board.toString());
//					System.out.println("Manhattan distance: " +enqueuingNode.board.manhattan() + " numMoves = "+enqueuingNode.numMoves);
			}
			*/
	}
	
	private class SearchNode implements Comparable<SearchNode>{
		Board board;
		int numMoves;
		SearchNode prevNode;
		int thisManhattan;
		int thatManhattan;
		
		public int compareTo(SearchNode that)
		{
			thisManhattan = this.board.manhattan();
			thatManhattan = that.board.manhattan();
			if((thisManhattan + this.numMoves) < (thatManhattan + that.numMoves))
					return -1;
			else if((thisManhattan + this.numMoves) > (thatManhattan + that.numMoves))
					return 1;
/*			else if((thisHamming + this.numMoves) < (thatHamming + that.numMoves))
					return -1;
			else if((thisHamming + this.numMoves) > (thatHamming + that.numMoves))
					return 1;
*/			
			return 0;
		}
	};
	
	public boolean isSolvable()            // is the initial board solvable?
	{
		return isSolvable;
	}

	public int moves()                     // min number of moves to solve initial board; -1 if unsolvable
	{
		if(!this.isSolvable())
			return -1;
//		return Math.max(this.initBoard.manhattan(), this.initBoard.hamming());
		//return this.initBoard.manhattan();
		return moves;
	}
	
	public Iterable<Board> solution()      // sequence of boards in a shortest solution; null if unsolvable
	{
		Queue<Board> solution = new Queue<Board>();
		Stack<SearchNode> revSolutionCopy = new Stack<SearchNode>();
	//	revSolutionCopy = revSolution.clone;
		
		if(!isSolvable)
			return null;
		while(revSolution.size()!=0)
		{
//			System.out.println("Num_moves: "+revSolution.peek().numMoves+" Size: "+revSolution.size());
			revSolutionCopy.push(revSolution.peek());
			solution.enqueue(revSolution.pop().board);
		}
		while(revSolutionCopy.size()!=0)
		{
			revSolution.push(revSolutionCopy.pop());
		}
		return solution;
	}
	
	public static void main(String[] args) // solve a slider puzzle (given below)
	{
		
/*		List<Integer> randList = new ArrayList<>();
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
		
		blockArg[0][0] = 8;
		blockArg[0][1] = 1;
		blockArg[0][2] = 3;
		blockArg[1][0] = 0;
		blockArg[1][1] = 2;
		blockArg[1][2] = 4;
		blockArg[2][0] = 6;
		blockArg[2][1] = 7;
		blockArg[2][2] = 5;

		Board initial = new Board(blockArg);
		
		Solver solver = new Solver(initial);
		
		System.out.println("Is it solvable? "+solver.isSolvable());
		System.out.println("Min number of moves "+solver.moves());
	*/	
		
		
		
		
		// create initial board from file
		In in = new In(args[0]);
		int n = in.readInt();
		int[][] blocks = new int[n][n];
		for (int i = 0; i < n; i++)
			for (int j = 0; j < n; j++)
				blocks[i][j] = in.readInt();
		Board initial = new Board(blocks);

		// solve the puzzle
		Solver solver = new Solver(initial);

		// print solution to standard output
		if (!solver.isSolvable())
			StdOut.println("No solution possible");
		else {
			StdOut.println("Minimum number of moves = " + solver.moves());
			for (Board board : solver.solution())
				StdOut.println(board);
		}

		if (!solver.isSolvable())
			StdOut.println("No solution possible");
		else {
			StdOut.println("Minimum number of moves = " + solver.moves());
			for (Board board : solver.solution())
				StdOut.println(board);
		}

	}	
}