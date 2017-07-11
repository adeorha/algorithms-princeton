import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

public class BruteCollinearPoints {
	private LineSegment[] lineSegmentarr;

	private List<LineSegment> lineSegments = new ArrayList<LineSegment>();
	public BruteCollinearPoints(Point[] points)    // finds all line segments containing 4 points
	{
		if(points == null){
		throw new java.lang.NullPointerException();
		}
		for(Point p: points)
		{
			if(p==null){
				throw new java.lang.NullPointerException();
			}
		}
		Point[] pointsCopy = Arrays.copyOf(points, points.length);
		Arrays.sort(pointsCopy);
		int i=0;
		while(i<pointsCopy.length-1){
			if(pointsCopy[i].compareTo(pointsCopy[++i]) == 0){
				throw new java.lang.IllegalArgumentException();
			}
		}
		
		for(i = 0; i<pointsCopy.length-3; i++){
			for(int j = i+1; j<pointsCopy.length-2; j++){
				for(int k = j+1; k<pointsCopy.length-1; k++){
					if(pointsCopy[i].slopeTo(pointsCopy[j]) == pointsCopy[i].slopeTo(pointsCopy[k])){
						for(int l = k+1; l<pointsCopy.length; l++)
							if(pointsCopy[i].slopeTo(pointsCopy[j]) == pointsCopy[i].slopeTo(pointsCopy[l])){
								lineSegments.add(new LineSegment(pointsCopy[i], pointsCopy[l]));
							}
						}
					}
				}
			}
		
		lineSegmentarr = new LineSegment[lineSegments.size()];
		lineSegmentarr = lineSegments.toArray(new LineSegment[0]);
	}
	
	public int numberOfSegments()        // the number of line segments
	{
		return lineSegments.size();
	}
	
	public LineSegment[] segments()                // the line segments
	{
		return lineSegmentarr.clone();
		//return Arrays.copyOf(lineSegmentarr, lineSegmentarr.length);
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
/*		Point points_arr[] = new Point[9];
		points_arr[0] = new Point(3,  5);
		points_arr[1] = new Point(3,  6);
		points_arr[2] = new Point(3,  7);
		points_arr[3] = new Point(3,  8);
		points_arr[4] = new Point(1,  1);
		points_arr[5] = new Point(2,  2);
		points_arr[6] = new Point(5,  5);
		points_arr[7] = new Point(4,  4);
		points_arr[8] = new Point(10,  1);
		BruteCollinearPoints brute = new BruteCollinearPoints(points_arr);
		System.out.println(brute.numberOfSegments());
		System.out.println(brute.segments()[1].toString());
		*/
		
	    // read the n points from a file
	    In in = new In(args[0]);
	    int n = in.readInt();
	    Point[] points = new Point[n];
	    for (int i = 0; i < n; i++) {
	        int x = in.readInt();
	        int y = in.readInt();
	        points[i] = new Point(x, y);
	    }

	    // draw the points
	    StdDraw.enableDoubleBuffering();
	    StdDraw.setXscale(0, 32768);
	    StdDraw.setYscale(0, 32768);
	    for (Point p : points) {
	        p.draw();
//	        System.out.println("points "+p.toString()+"\t");
	    }
	    StdDraw.show();

	    // print and draw the line segments
	    BruteCollinearPoints collinear = new BruteCollinearPoints(points);
	    for (LineSegment segment : collinear.segments()) {
	        StdOut.println(segment);
	        segment.draw();
	    }
	    StdDraw.show();
		

	}

}
