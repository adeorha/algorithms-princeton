import java.util.ArrayList;

import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.SET;
import edu.princeton.cs.algs4.StdDraw;

public class PointSET {
	private SET<Point2D> set;
	public PointSET()                               // construct an empty set of points 
	{
		set = new SET<Point2D>();
	}

	public boolean isEmpty()                      // is the set empty? 
	{
		return (set.size()==0);
	}

	public int size()                         // number of points in the set 
	{
		return set.size();
	}

	public void insert(Point2D p)              // add the point to the set (if it is not already in the set)
	{
		if(!set.contains(p))
		{
			set.add(p);
		}
	}

	public boolean contains(Point2D p)            // does the set contain point p? 
	{
		return set.contains(p);
	}

	public void draw()                         // draw all points to standard draw 
	{
		for (Point2D p : set) {
			p.draw();
			//		        System.out.println("points "+p.toString()+"\t");
		}
		StdDraw.show();
	}

	public Iterable<Point2D> range(RectHV rect)             // all points that are inside the rectangle 
	{
		ArrayList<Point2D> pointsList = new ArrayList<Point2D>();
		for(Point2D p: set){
			if(rect.contains(p))
				pointsList.add(p);
		}

		return pointsList;
	}

	public Point2D nearest(Point2D p)             // a nearest neighbor in the set to point p; null if the set is empty 
	{
		double minDist = 1.5;
		double dist;
		Point2D returnPoint = p;
		if(set.isEmpty())
			return null;
		else
		{
			for(Point2D q: set)
			{
				dist = p.distanceTo(q);
				if(dist<minDist)
				{
					minDist=dist;
					returnPoint = q;
				}
			}
			return returnPoint;
		}
	}

	public static void main(String[] args)                  // unit testing of the methods (optional) 
	{
		PointSET brute = new PointSET();
		
		Point2D[] p = new Point2D[10];

		p[0] = new Point2D(0.206107, 0.095492);
		p[1] = new Point2D(0.975528, 0.654508);
		p[2] = new Point2D(0.024472, 0.345492);
		p[3] = new Point2D(	0.793893, 0.095492);
		p[4] = new Point2D(0.793893, 0.904508);
		p[5] = new Point2D(0.975528, 0.345492);
		p[6] = new Point2D(0.206107, 0.904508);
		p[7] = new Point2D(0.500000, 0.000000);
		p[8] = new Point2D(0.024472, 0.654508);
		p[9] = new Point2D(0.500000, 1.000000);
	
		for(int i=0; i<10; i++)
		{
			brute.insert(p[i]);
			System.out.println(p[i]);
		}
		
		Point2D queryPoint = new Point2D(0,  0.6);
		System.out.println("Nearest point to " + queryPoint.x() + ","+queryPoint.y()+" = "+brute.nearest(queryPoint));
	}

}