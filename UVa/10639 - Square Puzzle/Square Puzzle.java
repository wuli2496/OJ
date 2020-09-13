import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;
import java.util.function.Predicate;

public class Main {
	
	public static void main(String[] args) throws IOException {
		Scanner scanner;
		
		PrintWriter printWriter = new PrintWriter(new BufferedOutputStream(System.out));
		
		if (DEBUG) {
			scanner = new Scanner(new BufferedInputStream(new FileInputStream("/home/wl/OJ/OJ/UVa/uvain")));
		} else {
			scanner = new Scanner(System.in);
		}
		
		int p = scanner.nextInt();
		for (int i = 0; i < p; ++i) {
			int n, m;
			n = scanner.nextInt();
			m = scanner.nextInt();
			
			Main solution = new Main();
			solution.n = n;
			solution.m = m;
			for (int j = 0; j < n; ++j) {
				Polygon polygon = new Polygon();
				int k = scanner.nextInt();
				polygon.pointNum = k;
				for (int a = 0; a < k; ++a) {
					int x = scanner.nextInt();
					int y = scanner.nextInt();
					Point point = new Point(x, y);
					polygon.points.add(point);
				}
				solution.polygons.add(polygon);
			}
			
			boolean ans = solution.solve(); 
			if (ans) { 
				  printWriter.println("yes"); 
			} else { 
				printWriter.println("no"); 
			}
		}
		
		scanner.close();
	}
	
	public boolean solve() {
		filled = new boolean[m][m];
		
		int[][][] puzzles = new int[n][][];
		for (int i = 0; i < n; ++i) {
			puzzles[i] = scanLineFill(polygons.get(i));
		}
		return false;
	}
	
	private int[][] scanLineFill(Polygon polygon) {
		int ymin = polygon.points.stream().map(Point::getY).min(Integer::compare).get();
		int ymax = polygon.points.stream().map(Point::getY).max(Integer::compare).get();
		int xmin = polygon.points.stream().map(Point::getX).min(Integer::compare).get();
		int xmax = polygon.points.stream().map(Point::getX).max(Integer::compare).get();
		
		List<Edge>[] netEdges;
		List[] originLists = new List[ymax - ymin];
		for (int y = ymin; y < ymax; ++y) {
			originLists[y - ymin] = new ArrayList<>(); 
		}
		netEdges = (List<Edge>[])originLists;
		initScanLineNewEdgeTable(netEdges, polygon, ymin, ymax);
		return calFill(netEdges, ymin, ymax, xmin, xmax);
	}
	
	private int[][] calFill(List<Edge>[] edgeLists, int ymin, int ymax, int xmin, int xmax) {
		List<Edge> aetEdges = new ArrayList<>(); 
		
		Comparator<Edge> edgeComparator = new Comparator<Edge>() {
			
			@Override
			public int compare(Edge o1, Edge o2) {
				// TODO Auto-generated method stub
				return o1.x - o2.x;
			}
		};
		int[][] fill = new int[ymax - ymin][xmax - xmin];
		for (int y = ymin; y < ymax; ++y) {
			insertNetListToAet(edgeLists[y - ymin], aetEdges);
			Collections.sort(aetEdges, edgeComparator);
			
			int size = aetEdges.size();
			for (int j = 0; j < size - 1; j += 2) {
				fillTwoPoint(aetEdges.get(j), aetEdges.get(j + 1), fill, xmin, y - ymin);
			}
			
			removeNonActiveEdgeFromAet(edgeLists[y - ymin], y);
			updateAndResortAet(edgeLists[y - ymin]);
		}
		
		return fill;
	}
	
	private void updateAndResortAet(List<Edge> edges) {
		edges.stream().forEach(edge -> {
			edge.x += edge.dx;
		});
		
		Collections.sort(edges, new Comparator<Edge>() {

			@Override
			public int compare(Edge o1, Edge o2) {
				// TODO Auto-generated method stub
				return o1.x - o2.x;
			}
		});
	}
	
	private void removeNonActiveEdgeFromAet(List<Edge> aet, int y) {
		aet.removeIf(new Predicate<Edge>() {

			@Override
			public boolean test(Edge t) {
				// TODO Auto-generated method stub
				return t.ymax == y;
			}
		});
	}

	private void fillTwoPoint(Edge a, Edge b, int[][] fill, int xmin, int y) {
		int xstart = a.x;
		int xend = b.x;
		if (a.dx == 1) {
			fill[y][xstart - xmin] = FillType.DIAG_RIGHT_DOWN.getCode();
			++xstart;
		}
		
		if (b.dx == -1) {
			fill[y][xend - 1 - xmin] = FillType.DIAG_LEFT_DOWN.getCode();
			--xend;
		}
		
		for (int x = xstart; x < xend; ++x) {
			fill[y][x - xmin] = FillType.FULL_FILL.getCode();
		}
	}
	
	private void insertNetListToAet(List<Edge> net, List<Edge> aet) {
		if (net.isEmpty()) {
			return;
		}
		
		aet.addAll(net);
	}
	
	private void initScanLineNewEdgeTable(List<Edge>[] lists, Polygon polygon, int ymin, int ymax) {
		for (int i = 0; i < polygon.pointNum; ++i) {
			Point startPoint = polygon.points.get(i);
			Point endPoint = polygon.points.get((i + 1) % polygon.pointNum);
			Point sstartPoint = polygon.points.get((i - 1 + polygon.pointNum) % polygon.pointNum);
			Point eendPoint = polygon.points.get((i + 2) % polygon.pointNum);
			
			if (startPoint.y == endPoint.y) {
				continue;
			}
			
			Edge edge = new Edge();
			edge.dx = (endPoint.x - startPoint.x) / (endPoint.y - startPoint.y);
			if (endPoint.y > startPoint.y) {
				edge.x = startPoint.x;
				if (eendPoint.y >= endPoint.y) {
					edge.ymax = endPoint.y - 1;
				} else {
					edge.ymax = endPoint.y;
				}
				
				lists[startPoint.y - ymin].add(0, edge);
			} else {
				edge.x = endPoint.x;
				if (sstartPoint.y >= startPoint.y) {
					edge.ymax = startPoint.y - 1;
				} else {
					edge.ymax = startPoint.y;
				}
				
				lists[endPoint.y - ymin].add(0, edge);
			}
		}
	}
	
	private boolean[][] filled;
	private static final boolean DEBUG = true;
	
	private int m, n;
	private List<Polygon> polygons = new ArrayList<>();
	
	static class Point {
		int x, y;
		
		Point(int x, int y) {
			this.x = x;
			this.y = y;
		}
		
		int getX() {
			return x;
		}
		
		int getY() {
			return y;
		}
		
	}
	
	static class Polygon {
		List<Point> points = new ArrayList<>();
		int pointNum;
	}
	
	static class Edge {
		int x;
		int dx;
		int ymax;
	}
}

