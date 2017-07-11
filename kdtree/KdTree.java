import java.util.ArrayList;

import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.SET;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdRandom;

public class KdTree {

	private Node root;
	private int size;
	private double minDist;

	private static class Node {
		private Point2D p;      // the point
		private RectHV rect;    // the axis-aligned rectangle corresponding to this node
		private Node lb;        // the left/bottom subtree
		private Node rt;        // the right/top subtree
		private boolean orientation;

		public Node(Point2D p, RectHV rect, boolean orientation){
			this.p = p;
			this.rect = rect;
			this.orientation = orientation;
		}
	}

	public KdTree()                               // construct an empty set of points 
	{
		size = 0;
		minDist = 1.5;
	}

	public boolean isEmpty()                      // is the set empty? 
	{
		return (size==0);
	}

	public int size()                         // number of points in the set 
	{
		return size;
	}

	public void insert(Point2D p)              // add the point to the set (if it is not already in the set)
	{
		if(p==null) throw new java.lang.NullPointerException();

		boolean orientation = true; //true = x coordinate, false = y coordinate
		RectHV rect = new RectHV(0, 0, 1, 1);
		if(contains(p))
			return;
		root = put(root, p, orientation, rect);
		size++;
	}

	private Node put(Node n, Point2D p, boolean orientation, RectHV prevRect)
	{
		int cmpX, cmpY;
		RectHV newRect;
		//System.out.println("orientation = "+orientation);
		if(n==null)
		{
			return new Node(p, prevRect, orientation);
		}

		else
		{
			if(n.p.x() <= p.x())
				cmpX = -1;
			else
				cmpX = 1;
			if(n.p.y() <= p.y())
				cmpY = -1;
			else
				cmpY = 1;

			if(orientation)
			{
				orientation = !orientation;
				if(cmpX == 1)
				{
					newRect = new RectHV(prevRect.xmin(), prevRect.ymin(), n.p.x(), prevRect.ymax());
					n.lb = put(n.lb, p, orientation, newRect);
				}
				else
				{
					newRect = new RectHV(n.p.x(), prevRect.ymin(), prevRect.xmax(), prevRect.ymax());
					n.rt = put(n.rt, p, orientation, newRect);
				}
			}
			else
			{
				orientation = !orientation;
				if(cmpY == 1)
				{
					newRect = new RectHV(prevRect.xmin(), prevRect.ymin(), prevRect.xmax(), n.p.y());
					n.lb = put(n.lb, p, orientation, newRect);
				}
				else
				{
					newRect = new RectHV(prevRect.xmin(), n.p.y(), prevRect.xmax(), prevRect.ymax());
					n.rt = put(n.rt, p, orientation, newRect);
				}
			}

		}
		return n;
	}

	public boolean contains(Point2D p)            // does the set contain point p? 
	{
		if(p==null) throw new java.lang.NullPointerException();

		boolean orientation = true; //true = x coordinate, false = y coordinate
		return get(root, p, orientation) != null;
	}


	private Node get(Node n, Point2D p, boolean orientation) {
		int cmpX, cmpY;
		if (p == null) throw new java.lang.NullPointerException();
		if (n == null) 
			return null;

		if(n.p.x() <= p.x())
			cmpX = -1;
		else
			cmpX = 1;
		if(n.p.y() <= p.y())
			cmpY = -1;
		else
			cmpY = 1;

		if(orientation)
		{
			orientation = !orientation;
			if((n.p.x() == p.x()) && (n.p.y() == p.y()))
				return n;
			else if(cmpX == 1)
				return get(n.lb, p, orientation);
			else
				return get(n.rt, p, orientation);
		}
		else
		{
			orientation = !orientation;
			if((n.p.x() == p.x()) && (n.p.y() == p.y()))
				return n;
			else if(cmpY == 1)
				return get(n.lb, p, orientation);
			else
				return get(n.rt, p, orientation);
		}
	}


	public void draw()                         // draw all points to standard draw 
	{
		printPreOrderRec(root);
		//System.out.println("");	
	}

	private void printPreOrderRec(Node currRoot){
		if (currRoot == null) {
			return;
		}
		StdDraw.setPenColor(StdDraw.BLACK);
		StdDraw.setPenRadius(0.01);
		StdDraw.point(currRoot.p.x(), currRoot.p.y());
		if(!currRoot.orientation)
		{
			StdDraw.setPenColor(StdDraw.BLUE);
			StdDraw.setPenRadius();		
			StdDraw.line(currRoot.rect.xmin(), currRoot.p.y(), currRoot.rect.xmax(), currRoot.p.y());
		}
		else if(currRoot.orientation)
		{
			StdDraw.setPenColor(StdDraw.RED);
			StdDraw.setPenRadius();		
			StdDraw.line(currRoot.p.x(), currRoot.rect.ymin(), currRoot.p.x(), currRoot.rect.ymax());
		}
		//		System.out.println(currRoot.p.x() + ", "+currRoot.p.y());
		printPreOrderRec(currRoot.lb);
		printPreOrderRec(currRoot.rt);
		StdDraw.show();
	}

	public Iterable<Point2D> range(RectHV rect)             // all points that are inside the rectangle 
	{
		ArrayList<Point2D> pointsList = new ArrayList<Point2D>();

		pointsInRect(root, pointsList, rect);

		return pointsList;
	}

	private void pointsInRect(Node node, ArrayList<Point2D> list, RectHV rect)
	{
		if(node == null)
			return;

		if(inRect(node.p, rect))
		{
			list.add(node.p);
		}

		if((node.lb!=null) && (rectIntersect(rect, node.lb.rect)))
		{
			pointsInRect(node.lb, list, rect);
		}

		if((node.rt!=null) && (rectIntersect(rect, node.rt.rect)))
		{
			pointsInRect(node.rt, list, rect);
		}
	}

	private boolean inRect(Point2D p, RectHV rect)
	{
		if((p.y()<=rect.ymax()) && (p.y()>=rect.ymin()) && (p.x()<=rect.xmax()) && (p.x()>=rect.xmin()))
			return true;
		else
			return false;
	}

	private boolean rectIntersect(RectHV queryRect, RectHV rect)
	{
		if((queryRect.xmin() > rect.xmax()) || (queryRect.ymin() > rect.ymax()) || (queryRect.xmax() < rect.xmin()) || (queryRect.ymax() < rect.ymin()))
			return false;
		else
			return true;
	}



	public Point2D nearest(Point2D p)             // a nearest neighbor in the set to point p; null if the set is empty 
	{
		//double minDist = 1.5;
		double dist;
		minDist = 1.5;
		Point2D closestPoint;
		if(size==0)
			return null;
		else
		{
			closestPoint = root.p;
			//			closestPoint = new Point2D(root.p.x(), root.p.y());
			closestPoint = minDist(p, root, closestPoint);
			//			closestPoint = minDist(p, root, minDist, closestPoint);
			return closestPoint;
		}
	}

	private Point2D minDist(Point2D p, Node node, Point2D closestPoint)
	//	private Point2D minDist(Point2D p, Node node, double minDist, Point2D closestPoint)
	{
		if(node == null)
			return closestPoint;

		else if(node.p.distanceTo(p) < minDist)
		{
			minDist = node.p.distanceTo(p);
			closestPoint = node.p;
		}

		if((node.orientation && (p.x() < node.p.x())) || (!node.orientation && (p.y() < node.p.y())))
		{
			if(node.lb != null && (minDist > node.lb.rect.distanceTo(p)))
				closestPoint = minDist(p, node.lb, closestPoint);

			if(node.rt != null && (minDist > node.rt.rect.distanceTo(p)))
				closestPoint = minDist(p, node.rt, closestPoint);
		}

		if((node.orientation && (p.x() >= node.p.x())) || (!node.orientation && (p.y() >= node.p.y())))
		{
			if(node.rt != null && (minDist > node.rt.rect.distanceTo(p)))
				closestPoint = minDist(p, node.rt, closestPoint);

			if(node.lb != null && (minDist > node.lb.rect.distanceTo(p)))
				closestPoint = minDist(p, node.lb, closestPoint);
		}


		/*	
		else if(node.rect.distanceTo(p) < minDist)
		{
			minDist = node.rect.distanceTo(p);
		}
		 */
		return closestPoint;
	}


	/**
	 * @param args
	 */
	/**
	 * @param args
	 */
	public static void main(String[] args)                  // unit testing of the methods (optional) 
	{

		KdTree tree = new KdTree();
		Point2D[] p = new Point2D[11];
		Point2D query = new Point2D(0.45, 0.55);
		/*for(int i=0; i<10; i++)
		{
			p[i] = new Point2D((i+1)*0.05, 1-(i+1)*0.05);
//			p[i] = new Point2D(StdRandom.uniform(0.0,1.0), StdRandom.uniform(0.0,1.0));
			tree.insert(p[i]);
			System.out.println(p[i]);
		}
		 */
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
		p[10] = new Point2D(1, 0);

		for(int i=0; i<11; i++)
		{
			tree.insert(p[i]);
			System.out.println(p[i]);
		}

		//		System.out.println(tree.size());
		//		System.out.println(tree.contains(query));
		//		System.out.println();
		tree.draw();
		RectHV rect = new RectHV(0.2, 0.01, 0.3, 0.9);
		/*		
		System.out.println("Points contained are");
		for(Point2D q: tree.range(rect))
		{
			System.out.println(q.x()+","+q.y());
		}
		 */	
		Point2D queryPoint = new Point2D(0,  0.6);
		System.out.println("Nearest point to " + queryPoint.x() + ","+queryPoint.y()+" = "+tree.nearest(queryPoint));
		Point2D rangePoint = new Point2D(1.0, 0.0);
		System.out.println("Is 1,0 present? "+tree.contains(rangePoint));

	}

}