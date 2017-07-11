/******************************************************************************
 *  Compilation:  javac Point.java
 *  Execution:    java Point
 *  Dependencies: none
 *  
 *  An immutable data type for points in the plane.
 *  For use on Coursera, Algorithms Part I programming assignment.
 *
 ******************************************************************************/

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import edu.princeton.cs.algs4.StdDraw;

public class Point implements Comparable<Point> {
	private final int x;     // x-coordinate of this point
	private final int y;     // y-coordinate of this point

    /**
     * Initializes a new point.
     *
     * @param  x the <em>x</em>-coordinate of the point
     * @param  y the <em>y</em>-coordinate of the point
     */
    public Point(int x, int y) {
        /* DO NOT MODIFY */
        this.x = x;
        this.y = y;
    }

    /**
     * Draws this point to standard draw.
     */
    public void draw() {
        /* DO NOT MODIFY */
        StdDraw.point(x, y);
    }

    /**
     * Draws the line segment between this point and the specified point
     * to standard draw.
     *
     * @param that the other point
     */
    public void drawTo(Point that) {
        /* DO NOT MODIFY */
        StdDraw.line(this.x, this.y, that.x, that.y);
    }

    /**
     * Returns the slope between this point and the specified point.
     * Formally, if the two points are (x0, y0) and (x1, y1), then the slope
     * is (y1 - y0) / (x1 - x0). For completeness, the slope is defined to be
     * +0.0 if the line segment connecting the two points is horizontal;
     * Double.POSITIVE_INFINITY if the line segment is vertical;
     * and Double.NEGATIVE_INFINITY if (x0, y0) and (x1, y1) are equal.
     *
     * @param  that the other point
     * @return the slope between this point and the specified point
     */
    public double slopeTo(Point that) {
        /* YOUR CODE HERE */
    	int num, den;
    	num = this.y - that.y;
    	den = this.x - that.x;
    	if((num==0) && (den==0))
    		return Double.NEGATIVE_INFINITY;
    	else if(num == 0)
    		return 0;
    	else if(den == 0)
    		return Double.POSITIVE_INFINITY;
    	else
    	return (double)num/den;
    }

    /**
     * Compares two points by y-coordinate, breaking ties by x-coordinate.
     * Formally, the invoking point (x0, y0) is less than the argument point
     * (x1, y1) if and only if either y0 < y1 or if y0 = y1 and x0 < x1.
     *
     * @param  that the other point
     * @return the value <tt>0</tt> if this point is equal to the argument
     *         point (x0 = x1 and y0 = y1);
     *         a negative integer if this point is less than the argument
     *         point; and a positive integer if this point is greater than the
     *         argument point
     */
    public int compareTo(Point that) {
        /* YOUR CODE HERE */
    	int ydiff, xdiff;
    	ydiff = this.y - that.y;
    	xdiff = this.x - that.x;
    	if(ydiff < 0)
    		return -1;
    	else if(ydiff > 0)
    		return 1;
    	else if(xdiff < 0)
    		return -1;
    	else if(xdiff > 0)
    		return 1;
    	else 
    		return 0;
    				
    }

    /**
     * Compares two points by the slope they make with this point.
     * The slope is defined as in the slopeTo() method.
     *
     * @return the Comparator that defines this ordering on points
     */
    public Comparator<Point> slopeOrder() {
        /* YOUR CODE HERE */
    	//return (a, b) -> Double.compare(slopeTo(a), slopeTo(b));
    	
    	return new Comparator<Point>(){
    		@Override
    		public int compare(Point a, Point b){
    			return Double.compare(slopeTo(a), slopeTo(b));
    		}
    	};
    }


    /**
     * Returns a string representation of this point.
     * This method is provide for debugging;
     * your program should not rely on the format of the string representation.
     *
     * @return a string representation of this point
     */
    public String toString() {
        /* DO NOT MODIFY */
        return "(" + x + ", " + y + ")";
    }

    /**
     * Unit tests the Point data type.
     */
    public static void main(String[] args) {
        /* YOUR CODE HERE */
    	List<Point> points = new ArrayList<Point>();

        points.add(new Point(4, 3));
        points.add(new Point(7, 10));
        points.add(new Point(1, 8));
        points.add(new Point(2, 2));
        points.add(new Point(-2, -2));
        
        for(Point point :points){
        	System.out.print(point.x+", "+point.y + "\t\t");
        }
        System.out.println();
        
        
        Collections.sort(points);
        System.out.println("Printing natural sort below");

        for(Point point :points){
        	System.out.print(point.x+", "+point.y + "\t\t");
        }
        System.out.println();
        
        System.out.println("Slope wrt point 1");
        for(Point point :points){
        	System.out.println(point.x+", "+point.y + " Slope = " + points.get(1).slopeTo(point)+"\t\t");
        }
        //Collections.sort(points, slopeOrder());
 
        //points.get(1).slopeOrder().compare(points.get(0), points.get(1));
        
        Collections.sort(points, points.get(1).slopeOrder());
        

        for(Point point :points){
        	System.out.print(point.x+", "+point.y + "\t\t");
        }
        
        
 /*   	pointObj1.drawTo(pointObj2);
    	pointObj1.drawTo(pointObj3);
    	pointObj2.drawTo(pointObj3);
    	pointObj1.draw();
    	pointObj2.draw();
 */
 /*   	System.out.println(pointObj2.slopeTo(pointObj1));
    	System.out.println("Pointobj1 > Pointobj2 is true?  " + pointObj1.compareTo(pointObj2));
    	System.out.println("pointObj1.slopeTo(pointObj3)" + pointObj1.slopeTo(pointObj3));
    	System.out.println("pointObj2.slopeTo(pointObj3)" + pointObj2.slopeTo(pointObj3));
    	System.out.println("" + pointObj1.compare(pointObj2, pointObj3));
    	System.out.println("Point1" + pointObj1.toString());
 */   }
}
