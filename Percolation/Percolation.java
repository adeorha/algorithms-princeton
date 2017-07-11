import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation{

	private int[][] grid;
	private int count; //dummy variable
	private int size; //n*n
	private int index_0, index_1, index_2, index_3, index_4;
	
	private WeightedQuickUnionUF grid_1d;
	
	private int xyTo1d(int row, int col){
		return ((row-1)*size + (col-1));
	}
	
	private boolean isIndexValid(int row, int col){
		if((row<1) || (col<1) || (row>size) || (col>size)){
			return false;
		}
		else
			return true;
	}
	
	public Percolation(int n){		// create n-by-n grid, with all sites blocked
		if(n<=0)
			throw new java.lang.IllegalArgumentException();
		grid_1d = new WeightedQuickUnionUF(n*n+2); //Map grid[0][0] (top) to grid_1d[n*n] and grid[0][1] (bottom) to grid_1d[n*n+1]
		grid = new int[n+1][n+1];
		size = n;
		count=0;
		for(int i=1; i<=n; i++){
			grid[i][i] = 0;
		}
		for(int i=1; i<=n; i++){
			grid_1d.union(xyTo1d(1, i), n*n);
			grid_1d.union(xyTo1d(n, i), n*n+1);
		}
	}
	public    void open(int row, int col) throws java.lang.IndexOutOfBoundsException {    // open site (row, col) if it is not open already
		if(!isIndexValid(row, col))
			throw new java.lang.IndexOutOfBoundsException();
		
		else if(grid[row][col]!=1){
			grid[row][col] = 1;
			count++;
			index_0 = xyTo1d(row, col);
			index_1 = xyTo1d(row-1, col);
			index_2 = xyTo1d(row, col+1);
			index_3 = xyTo1d(row+1, col);
			index_4 = xyTo1d(row, col-1);
			if(isIndexValid(row-1, col) && (grid[row-1][col] == 1)){
				grid_1d.union(index_0, index_1);
			}
			if(isIndexValid(row, col+1) && (grid[row][col+1] == 1)){
				grid_1d.union(index_0, index_2);
			}
			if(isIndexValid(row+1, col) && (grid[row+1][col] == 1)){
				grid_1d.union(index_0, index_3);
			}
			if(isIndexValid(row, col-1) && (grid[row][col-1] == 1)){
				grid_1d.union(index_0, index_4);
			}
//Make adjacent to connect				
		}
	}
	
	
	public boolean isOpen(int row, int col){  // is site (row, col) open?
		if(!isIndexValid(row, col))
			throw new java.lang.IndexOutOfBoundsException();
	
		else if(grid[row][col] == 1)
			return true;
		else return false;
		
	}
	public boolean isFull(int row, int col){  // is site (row, col) full?
		if(!isIndexValid(row, col))
			throw new java.lang.IndexOutOfBoundsException();
	
		else if(grid[row][col] == 1)
			if(grid_1d.connected(xyTo1d(row, col), size*size))
				return true;
			else
				return false;
		else return false;
		
	}
	public     int numberOfOpenSites(){       // number of open sites
		
		return count;
	}
	public boolean percolates(){              // does the system percolate?
		if(size==1){
			if(!isOpen(1, 1))
				return false;
			else
				return true;
		}
		else if(grid_1d.connected(size*size, size*size+1))
			return true;
		else return false;
	}

	public static void main(String[] args){   // test client (optional)
		Percolation test_obj = new Percolation(10);
		System.out.println("Hello, World");
		System.out.println("count = " + test_obj.count + "\nrow=2, col = 2, xyTo1d = " + test_obj.xyTo1d(2, 2));
		System.out.println("isIndexValid(2,2) = " + test_obj.isIndexValid(2,2));

	}
}