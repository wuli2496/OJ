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
			printWriter.flush();
		}
		
		scanner.close();
	}
	
	public boolean solve() {
		filled = new int[m][m];
		
		List<List<int[][]>> puzzles = initList(n);
		for (int i = 0; i < n; ++i) {
			int[][] fill = scanLineFill(polygons.get(i));
			
			initFeasibleFill(puzzles.get(i), fill);
		}
		
		//printPuzzles(puzzles);
		boolean[] visited = new boolean[n];
		boolean ok = dfs(0, puzzles, filled, visited);
		return ok;
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
			fill[y - ymin][xstart - xmin] = FillType.DIAG_RIGHT_DOWN.getCode();
			++xstart;
		} else if (a.dx == -1) {
			fill[y - ymin][xstart - 1 - xmin] = FillType.DIAG_RIGHT_UP.getCode();
		}
		
		if (b.dx == -1) {
			fill[y - ymin][xend - 1 - xmin] = FillType.DIAG_LEFT_DOWN.getCode();
			--xend;
		} else if (b.dx == 1) {
			fill[y - ymin][xend - xmin] = FillType.DIAG_LEFT_UP.getCode();
		}
		
		for (int x = xstart; x < xend; ++x) {
			fill[y - ymin][x - xmin] = FillType.FULL_FILL.getCode();
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
	
	private void printPuzzles(List<List<int[][]>> puzzles) {
		System.out.println("puzzles.size():" + puzzles.size());
		for (int i = 0; i < puzzles.size(); ++i) {
			List<int[][]> puzzle = puzzles.get(i);
			System.out.println("puzzle.size():" + puzzle.size());
			System.out.println("---------------");
			for (int j = 0; j < puzzle.size(); ++j) {
				System.out.println(Arrays.deepToString(puzzle.get(j)));
			}
			System.out.println("---------------");
		}
	}
	
	private int[][] rotate(int[][] fill) {
		int row = fill.length;
		int col = fill[0].length;
		
		int[][] result = new int[col][row];
		
		for (int i = 0; i < row; ++i) {
			for (int j = 0; j < col; ++j) {
				if (fill[i][j] == FillType.NOT_FILL.getCode() || fill[i][j] == FillType.FULL_FILL.getCode()) {
					result[col - 1 - j][i] = fill[i][j];
				} else if (fill[i][j] == FillType.DIAG_LEFT_UP.getCode()) {
					result[col - 1 - j][i] = FillType.DIAG_RIGHT_UP.getCode();
				} else if (fill[i][j] == FillType.DIAG_RIGHT_UP.getCode()) {
					result[col - 1 - j][i] = FillType.DIAG_RIGHT_DOWN.getCode();
				} else if (fill[i][j] == FillType.DIAG_RIGHT_DOWN.getCode()) {
					result[col - 1 - j][i] = FillType.DIAG_LEFT_DOWN.getCode();
				} else if (fill[i][j] == FillType.DIAG_LEFT_DOWN.getCode()) {
					result[col - 1 - j][i] = FillType.DIAG_LEFT_UP.getCode();
				}
			}
		}
		return result;
	}
	
	private boolean fillEqual(int[][] source, int[][] target) {
		int sourceRow = source.length;
		int sourceCol = source[0].length;
		
		int targetRow = target.length;
		int targetCol = target[0].length;
		
		if (sourceRow != targetRow) {
			return false;
		}
		
		if (sourceCol != targetCol) {
			return false;
		}
		
		for (int i = 0; i < sourceRow; ++i) {
			for (int j = 0; j < sourceCol; ++j) {
				if (source[i][j] != target[i][j]) {
					return false;
				}
			}
		}
		
		return true;
	}
	
	private List<List<int[][]>> initList(int n) {
		List<List<int[][]>> puzzles = new ArrayList<>();
		for (int i = 0; i < n; ++i) {
			puzzles.add(new ArrayList<>());
		}
		
		return puzzles;
	}
	
	private void initFeasibleFill(List<int[][]> list, int[][] fill) {
		list.add(fill);
		int[][] result = fill;
		for (int i = 0; i < 3; ++i) {
			result = rotate(result);
			boolean exist = false;
			for (int j = 0; j < list.size(); ++j) {
				if (fillEqual(list.get(j), result)) {
					exist = true;
					break;
				}
			}
			
			if (!exist) {
				list.add(result);
			}
		}
	}
	
	boolean dfs(int cur, List<List<int[][]>> fills, int[][] filled, boolean[] visited) {
		if (cur == n) {
			boolean ok = true;
			for (int i = 0; i < m; ++i) {
				for (int j = 0; j < m; ++j) {
					if (filled[i][j] != FillType.FULL_FILL.getCode()) {
						ok = false;
						break;
					}
				}
			}
			
			return ok;
		}
		
		int startx = 0, starty = 0;
		boolean found = false;
		for (int i = 0; i < m && !found; ++i) {
			for (int j = 0; j < m && !found; ++j) {
				if (filled[i][j] != FillType.FULL_FILL.getCode()) {
					found = true;
					startx = i;
					starty = j;
					break;
				}
			}
		}
		
		for (int i = 0; i < n; ++i) {
			if (!visited[i]) {
				visited[i] = true;
				List<int[][]> curFeasibleFill = fills.get(i);
				
				for (int k = 0; k < curFeasibleFill.size(); ++k) {
					int[][] curFill = curFeasibleFill.get(k);
					int row = curFill.length;
					int col = curFill[0].length;
					if (startx + row > m) continue;
					if (starty + col > m) continue;
					
					
					fillCurSquare(curFill, startx, starty, filled);
					boolean ok = checkCurFillSquare(curFill, startx, starty, filled);
					if (!ok) {
						unFillCurSquare(curFill, startx, starty, filled);
						continue;
					}
					
					if (dfs(cur + 1, fills, filled, visited)) {
						return true;
					} 
					unFillCurSquare(curFill, startx, starty, filled);
						
				}
				
				visited[i] = false;
			}
		}
		List<int[][]> curFeasibleFill = fills.get(cur);
		for (int i = 0; i < m; ++i) {
			for (int j = 0; j < m; ++j) {
				if (filled[i][j] != FillType.FULL_FILL.getCode()) {
					for (int k = 0; k < curFeasibleFill.size(); ++k) {
						int[][] curFill = curFeasibleFill.get(k);
						int row = curFill.length;
						int col = curFill[0].length;
						if (i + row > m) continue;
						if (j + col > m) continue;
						
						for (int a = 0; a <= m - row; ++a) {
							for (int b = 0; b <= m - col; ++b) {
								fillCurSquare(curFill, a, b, filled);
								boolean ok = checkCurFillSquare(curFill, a, b, filled);
								if (!ok) {
									unFillCurSquare(curFill, a, b, filled);
									continue;
								}
								
								if (dfs(cur + 1, fills, filled, visited)) {
									return true;
								} 
								unFillCurSquare(curFill, a, b, filled);
							}
						}
					}
				}
			}
		}
		
		return false;
	}
	
	private void fillCurSquare(int[][] square, int xoffset, int yoffset, int[][] filled) {
		int row = square.length;
		int col = square[0].length;
		
		for (int i = 0; i < row; ++i) {
			for (int j = 0; j < col; ++j) {
				filled[xoffset + i][yoffset + j] += square[i][j];
			}
		}
	}
	
	private void unFillCurSquare(int[][] square, int xoffset, int yoffset, int[][] filled) {
		int row = square.length;
		int col = square[0].length;
		
		for (int i = 0; i < row; ++i) {
			for (int j = 0; j < col; ++j) {
				filled[xoffset + i][yoffset + j] -= square[i][j];
			}
		}
	}
	
	private boolean checkCurFillSquare(int[][] square, int xoffset, int yoffset, int[][] filled) {
		int row = square.length;
		int col = square[0].length;
		
		for (int i = 0; i < row; ++i) {
			for (int j = 0; j < col; ++j) {
				if (filled[i][j] != FillType.NOT_FILL.getCode() && 
						filled[i][j] != FillType.FULL_FILL.getCode() &&
						filled[i][j] != FillType.DIAG_LEFT_UP.getCode() &&
						filled[i][j] != FillType.DIAG_RIGHT_DOWN.getCode() &&
						filled[i][j] != FillType.DIAG_LEFT_DOWN.getCode() &&
						filled[i][j] != FillType.DIAG_RIGHT_UP.getCode()) {
					return false;
				}
			}
		}
		
		return true;
	}
	
	
	private int[][] filled;
	private static final boolean DEBUG = false;
	
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
