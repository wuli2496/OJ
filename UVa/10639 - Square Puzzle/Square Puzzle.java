import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;
import java.util.function.Predicate;
import java.io.BufferedInputStream;

public class Main {
	public static void main(String[] args) throws IOException {
		Scanner scanner;
		
		PrintWriter printWriter = new PrintWriter(new BufferedOutputStream(System.out));
		
		if (DEBUG) {
			scanner = new Scanner(new BufferedInputStream(new FileInputStream("f:\\OJ\\uva_in.txt")));
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
			//System.out.println(Arrays.deepToString(puzzles[i]));
		}
		return false;
	}
	
	private int[][] scanLineFill(Polygon polygon) {
		int ymin = polygon.points.stream().map(Point::getY).min(Integer::compare).get();
		int ymax = polygon.points.stream().map(Point::getY).max(Integer::compare).get();
		int xmin = polygon.points.stream().map(Point::getX).min(Integer::compare).get();
		int xmax = polygon.points.stream().map(Point::getX).max(Integer::compare).get();
		
		//System.out.println("ymax:"+ ymax + " ymin:" + ymin);
		List<Edge>[] netEdges;
		List[] originLists = new List[ymax - ymin + 1];
		for (int y = ymin; y <= ymax; ++y) {
			originLists[y - ymin] = new ArrayList<>(); 
		}
		netEdges = (List<Edge>[])originLists;
		initScanLineNewEdgeTable(netEdges, polygon, ymin, ymax);
		//printNewEdgeTable(netEdges);
		int[][] fill = new int[ymax - ymin][xmax - xmin];
		horizonEdgeFill(polygon, fill, xmin, ymin, ymax);
		calFill(netEdges, ymin, ymax, xmin, xmax, fill);
		return fill;
	}
	
	private int[][] calFill(List<Edge>[] edgeLists, int ymin, int ymax, int xmin, int xmax, int[][] fill) {
		List<Edge> aetEdges = new ArrayList<>(); 
		
		for (int y = ymin; y <= ymax; ++y) {
			insertNetListToAet(edgeLists[y - ymin], aetEdges);
			
			fillAetScanLine(aetEdges, fill, xmin, xmax, y, ymin, ymax);
			
			removeNonActiveEdgeFromAet(aetEdges, y);
			updateAndResortAet(aetEdges);
		}
		
		return fill;
	}
	
	private void horizonEdgeFill(Polygon polygon, int[][] fill, int xmin, int ymin, int ymax) {
		for (int i = 0; i < polygon.pointNum; ++i) {
			Point startPoint = polygon.points.get(i);
			Point endPoint = polygon.points.get((i + 1) % polygon.pointNum);
			if (startPoint.y != endPoint.y) {
				continue;
			}
			
			int cury = startPoint.y;
			if (cury == ymax) {
				break;
			}
			int startx = Math.min(startPoint.x, endPoint.x);
			int endx = Math.max(startPoint.x, endPoint.x);
			for (int j = startx; j < endx; ++j) {
				fill[cury - ymin][j - xmin] = FillType.FULL_FILL.getCode();
			}
		}
	}
	
	private void updateAndResortAet(List<Edge> edges) {
		edges.stream().forEach(edge -> {
			edge.x += edge.dx;
		});
		
		edges.sort(Comparator.comparing(Edge::getx));
	}
	
	private void removeNonActiveEdgeFromAet(List<Edge> aet, int y) {
		aet.removeIf(new Predicate<Edge>() {

			@Override
			public boolean test(Edge t) {
				return t.ymax == y;
			}
		});
	}

	private void fillAetScanLine(List<Edge> aetEdges, int[][] fill, int xmin, int xmax, int y, int ymin, int ymax) {
		int size = aetEdges.size();
		for (int j = 0; j < size - 1; j += 2) {
			fillTwoPoint(aetEdges.get(j), aetEdges.get(j + 1), fill, xmin, xmax, y, ymin, ymax);
		}
	}
	
	private void fillTwoPoint(Edge a, Edge b, int[][] fill, int xmin, int xmax, int y, int ymin, int ymax) {
		if (y == ymax) {
			return;
		}
		int xstart = a.x;
		int xend = b.x;
		
		if (a.dx == 1) {
			fill[y][xstart - xmin] = FillType.DIAG_RIGHT_DOWN.getCode();
			++xstart;
		} else if (a.dx == -1) {
			fill[y][xstart - 1 - xmin] = FillType.DIAG_RIGHT_UP.getCode();
			++xstart;
		}
		
		if (b.dx == -1) {
			fill[y][xend - 1 - xmin] = FillType.DIAG_LEFT_DOWN.getCode();
			--xend;
		} else if (b.dx == 1) {
			fill[y][xend - xmin] = FillType.DIAG_LEFT_UP.getCode();
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
		
		aet.sort(Comparator.comparing(Edge::getx));
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
	
	private void printNewEdgeTable(List<Edge>[] netEdges) {
		System.out.println("length:" + netEdges.length);
		for (int i = 0; i < netEdges.length; ++i) {
			if (netEdges[i].size() == 0) {
				System.out.println("empty");
				continue;
			}
			System.out.println("----------------");
			for (int j = 0; j < netEdges[i].size(); ++j) {
				System.out.println("x:" + netEdges[i].get(j).x + " dx:" + netEdges[i].get(j).dx + " ymax:" + netEdges[i].get(j).ymax);
			}
			System.out.println("----------------");
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
		
		int getx() {
			return x;
		}
	}
	
	enum FillType {
		NOT_FILL(0),
		FULL_FILL(1),
		DIAG_LEFT_UP(-1),
		DIAG_RIGHT_DOWN(2),
		DIAG_LEFT_DOWN(-3),
		DIAG_RIGHT_UP(4);
		
		private int code;
		
		private FillType(int code) {
			this.code = code;
		}
		
		public int getCode() {
			return code;
		}
	}

}
