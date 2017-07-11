import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

public class FastCollinearPoints {
	//private ArrayList<Point> points_list = new ArrayList<Point>(); 
	private LineSegment[] lineSegmentarr;
	private boolean checkedBefore(Point[] points, Point p,int hi)
	{
		for(int i=0; i<=hi; i++)
		{
			if(p.compareTo(points[i]) == 0)
				return true;
		}
		return false;
	}
	
	public FastCollinearPoints(Point[] points)     // finds all line segments containing 4 or more points
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
		List<LineSegment> lineSegments = new ArrayList<LineSegment>();
		
		Point[] pointsCopy = Arrays.copyOf(points, points.length);
		Arrays.sort(pointsCopy);
		int i=0;
		while(i<pointsCopy.length-1){
			if(pointsCopy[i].compareTo(pointsCopy[++i]) == 0){
				throw new java.lang.IllegalArgumentException();
			}
		}	

		Point[] tempPoints;
		double[] slope = new double[pointsCopy.length];
		for(int a = 0; a<pointsCopy.length; a++)
		{
			//System.out.println("pointsCopy["+a+"] = "+pointsCopy[a]);
		}
		for(int idx = 0; idx<pointsCopy.length - 3; idx++)
		{
			tempPoints = Arrays.copyOf(pointsCopy, pointsCopy.length);
			Arrays.sort(tempPoints, pointsCopy[idx].slopeOrder()); //Sort the temp array according to slopes
			for(int a = 0; a<pointsCopy.length; a++)
			{
				//System.out.println("tempPoints["+a+"] = "+tempPoints[a]);
			}
			//System.out.println("Sorting array based on slope wrt to pointsCopy["+(idx)+"] = " + pointsCopy[idx].toString() + "with index_lo = " +(idx+1)+ "and index_hi = "+(pointsCopy.length-1));
			//System.out.println();
			for(i=0; i<pointsCopy.length; i++)
			{
				slope[i] = pointsCopy[idx].slopeTo(tempPoints[i]);	//Create the array storing slope wrt pointsCopy[idx]
	//			//System.out.println("pointsCopy["+idx+"] = "+pointsCopy[idx]+" tempPoints["+i+"] = "+tempPoints[i]);
			}
			//System.out.println();
			for(i=0; i<pointsCopy.length; i++)
			{
				slope[i] = pointsCopy[idx].slopeTo(tempPoints[i]);	//Create the array storing slope wrt pointsCopy[idx]
				//System.out.print("slope["+i+"] = "+slope[i] + "\t");
			}
			//System.out.println();
			for(i=0; i<slope.length-2; i++)
			{
				////System.out.println("In for loop, i=" + i);
				if(idx == 17)
				{
					//System.out.println("slope["+i+"] ="+slope[i]+"slope["+(i+1)+"] ="+slope[i+1]+"slope["+(i+2)+"] ="+slope[i+2]);
					//System.out.println("tempPoints["+i+"] ="+tempPoints[i]+"tempPoints["+(i+1)+"] ="+tempPoints[i+1]+"tempPoints["+(i+2)+"] ="+tempPoints[i+2]);
					//System.out.println("checkedBefore(pointsCopy, tempPoints[i], idx-1) = "+ checkedBefore(pointsCopy, tempPoints[i], idx-1)+ " checkedBefore(pointsCopy, tempPoints[i+1], idx-1) = "+ checkedBefore(pointsCopy, tempPoints[i+1], idx-1)+" checkedBefore(pointsCopy, tempPoints[i+2], idx-1) = "+ checkedBefore(pointsCopy, tempPoints[i+2], idx-1));
				}
				while((slope[i] == slope[i+1]) && (slope[i] == slope[i+2]) && !(checkedBefore(pointsCopy, tempPoints[i], idx-1)) && !(checkedBefore(pointsCopy, tempPoints[i+1], idx-1)) && !(checkedBefore(pointsCopy, tempPoints[i+2], idx-1)))
				{
					//System.out.println("Collinear");
					if(i==(slope.length-3)){
						lineSegments.add(new LineSegment(pointsCopy[idx], tempPoints[i+2]));
						break;
					}
					i=i+2;
					while((i<slope.length-1) && (slope[i] == slope[i+1]) && !(checkedBefore(pointsCopy, tempPoints[i+1], idx-1)))
					{
						i++;
					}
					lineSegments.add(new LineSegment(pointsCopy[idx], tempPoints[i]));
				}

			}
		}
		lineSegmentarr = new LineSegment[lineSegments.size()];
		lineSegmentarr = lineSegments.toArray(new LineSegment[0]);
	}
	
	
	public int numberOfSegments()        // the number of line segments
	{
		return lineSegmentarr.length;
	}
	public LineSegment[] segments()                // the line segments
	{
	//	return Arrays.copyOf(lineSegmentarr, lineSegmentarr.length);
		return lineSegmentarr.clone();
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		/*
		Point[] points = new Point[8];
		
		points[0] = new Point(3,  5);
		points[1] = new Point(3, 7);
		points[2] = new Point(1, 1);
		points[3] = new Point(8,  8);
		points[4] = new Point(3, 6);
		points[5] = new Point(3,  3);
		points[6] = new Point(5,  5);
		points[7] = new Point(4,  7);
		

		for (Point point: points)
		{
        	System.out.print(point.toString()+ "\t\t");
		}
		
		System.out.println();
		FastCollinearPoints fcpObj = new FastCollinearPoints(points);
		for (Point point: points)
		{
        	System.out.print(point.toString()+ "\t\t");
		}
		System.out.println();
		System.out.println("Number of line segments = "+fcpObj.numberOfSegments());
		
		for(LineSegment lineseg: fcpObj.segments())
		{
			System.out.println("lineSeg:"+ lineseg.toString());
		}
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
	    }
	    StdDraw.show();

	    // print and draw the line segments
	    FastCollinearPoints collinear = new FastCollinearPoints(points);
	    for (LineSegment segment : collinear.segments()) {
	        StdOut.println(segment);
	        segment.draw();
	    }
	    StdDraw.show();
		
	}

}
