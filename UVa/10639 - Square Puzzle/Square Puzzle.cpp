#include <iostream>
#include <fstream>
#include <algorithm>
#include <vector>
#include <cstring>
#include <functional>

using namespace std;

const int MAXM = 7;
const int NOT_FILL = 0, FULL_FILL = 1, DIAG_LEFT_UP = -1, DIAG_RIGHT_DOWN = 2, DIAG_LEFT_DOWN = -3, DIAG_RIGHT_UP = 4;

struct Point
{
    int x, y;
};

struct Polygon
{
    vector<Point> points;
};

struct Edge
{
    int x, dx, ymax;

    bool operator < (const Edge& other) const
    {
        return x < other.x;
    }
};

struct FillSquare
{
    int fill[MAXM][MAXM];
    int row, col;
};

int n, m;
vector<Polygon> polygons;
FillSquare filled;
vector<vector<FillSquare>> puzzles;
int xmin, xmax, ymin, ymax;

void fastio()
{
    ios_base::sync_with_stdio(false);
    cin.tie(NULL);
    cout.tie(NULL);
}

void printNewEdgeTable(vector<vector<Edge>>& netEdges) 
{
    cout << "length:" << netEdges.size() << endl;
    for (int i = 0; i < netEdges.size(); ++i) {
        if (netEdges[i].size() == 0) {
            cout << "empty" << endl;
            continue;
        }
        cout << "----------------" << endl;
        for (int j = 0; j < netEdges[i].size(); ++j) {
            cout << "x:" << netEdges[i][j].x  << " dx:"  << netEdges[i][j].dx  << " ymax:" << netEdges[i][j].ymax << endl;
        }
        cout << "----------------" << endl;
    }
}

void printPuzzles(vector<vector<FillSquare>> puzzles) 
{
    cout << "puzzles.size():" << puzzles.size() << endl;
    for (int i = 0; i < puzzles.size(); ++i) {
        vector<FillSquare>& puzzle = puzzles[i];
        cout << "puzzle.size():"  << puzzle.size() << endl;
        cout <<"---------------" << endl;
        for (int j = 0; j < puzzle.size(); ++j) {
            FillSquare& fillSquare = puzzle[j];
            for (int a = 0; a < fillSquare.row; ++a) {
                for (int b = 0; b < fillSquare.col; ++b) {
                    cout << fillSquare.fill[a][b] << " ";
                }
                cout << endl;
            }
        }
        cout <<"---------------" << endl;
    }
}
void calXYMinMax(Polygon& polygon, int& xmin, int& xmax, int& ymin, int& ymax)
{
    xmin = ymin = INT_MAX;
    xmax = ymax = INT_MIN;

    for (int i = 0; i < polygon.points.size(); ++i) {
        const Point& point = polygon.points[i];

        xmin = min(xmin, point.x);
        xmax = max(xmax, point.x);

        ymin = min(ymin, point.y);
        ymax = max(ymax, point.y);
    }
}



void initScanLineNewEdgeTable(vector<vector<Edge>>& edges, const Polygon& polygon)
{
    int pointNum = polygon.points.size();
    for (int i = 0; i < pointNum; ++i) {
        const Point& startPoint = polygon.points[i];
        const Point& endPoint = polygon.points[(i + 1) % pointNum];
        const Point& sstartPoint = polygon.points[(i - 1 + pointNum) % pointNum];
        const Point& eendPoint = polygon.points[(i + 2) % pointNum];

        if (startPoint.y == endPoint.y) {
            continue;
        }

        Edge edge;
        edge.dx = (endPoint.x - startPoint.x) / (endPoint.y - startPoint.y);
        if (endPoint.y > startPoint.y) {
            edge.x = startPoint.x;
            if (eendPoint.y >= endPoint.y) {
                edge.ymax = endPoint.y - 1;
            } else {
                edge.ymax = endPoint.y;
            }

            edges[startPoint.y - ymin].insert(edges[startPoint.y - ymin].begin(), edge);
        } else {
            edge.x = endPoint.x;
            if (sstartPoint.y >= startPoint.y) {
                edge.ymax = startPoint.y - 1;
            } else {
                edge.ymax = startPoint.y;
            }

            edges[endPoint.y - ymin].insert(edges[endPoint.y - ymin].begin(), edge);
        }
    }
}

void insertNetListToAet(vector<Edge>& net, vector<Edge>& aet)
{
    if (net.size() == 0) {
        return;
    }

    for (auto& e : net) {
        aet.push_back(e);
    }

    sort(aet.begin(), aet.end());
}

void fillTwoPoint(Edge& a, Edge& b, FillSquare& fillSquare, int y)
{
    if (y == ymax) {
        return;
    }
    int xstart = a.x;
    int xend = b.x;

    if (a.dx == 1) {
        fillSquare.fill[y - ymin][xstart - xmin] = DIAG_RIGHT_DOWN;
        ++xstart;
    } else if (a.dx == -1) {
        fillSquare.fill[y - ymin][xstart - 1 - xmin] = DIAG_RIGHT_UP;
    }

    if (b.dx == -1) {
        fillSquare.fill[y - ymin][xend - 1 - xmin] = DIAG_LEFT_DOWN;
        --xend;
    } else if (b.dx == 1) {
        fillSquare.fill[y - ymin][xend - xmin] = DIAG_LEFT_UP;
    }

    for (int x = xstart; x < xend; ++x) {
        fillSquare.fill[y - ymin][x - xmin] = FULL_FILL;
    }
}

void fillAetScanLine(vector<Edge>& aetEdges, FillSquare& fillSquare, int y)
{
    int size = aetEdges.size();
    for (int i = 0; i < size - 1; i += 2) {
        fillTwoPoint(aetEdges[i], aetEdges[i + 1], fillSquare, y);
    }
}

bool isEdgeOutOfActive(Edge e, int y)
{
    return e.ymax == y;
}

void removeNonActiveEdgeFromAet(vector<Edge>& aet, int y)
{
    aet.erase(remove_if(aet.begin(), aet.end(), bind2nd(ptr_fun(isEdgeOutOfActive), y)));
}

void updateAetEdgeInfo(Edge& e)
{
    e.x += e.dx;
}

void updateAndResortAet(vector<Edge>& aet)
{
    for_each(aet.begin(), aet.end(), updateAetEdgeInfo);
    sort(aet.begin(), aet.end());
}

void calFill(vector<vector<Edge>>& edges, FillSquare& fillSquare)
{
    vector<Edge> activeEdges;
    for (int y = ymin; y <= ymax; ++y) {
        insertNetListToAet(edges[y - ymin], activeEdges);

        fillAetScanLine(activeEdges, fillSquare, y);

        removeNonActiveEdgeFromAet(activeEdges, y);
        updateAndResortAet(activeEdges);
    }
}

void scanLineFill(Polygon& polygon, FillSquare& fillSquare)
{
    int xmin, xmax, ymin, ymax;
    calXYMinMax(polygon, xmin, xmax, ymin, ymax);
    vector<vector<Edge>> edges(ymax - ymin + 1);

    initScanLineNewEdgeTable(edges, polygon);
    printNewEdgeTable(edges);
    fillSquare.row = ymax - ymin;
    fillSquare.col = xmax - xmin;
    calFill(edges, fillSquare);
}

FillSquare rotate(FillSquare& source)
{
    int row = source.row;
    int col = source.col;

    FillSquare result;
    result.row = col;
    result.col = row;

    for (int i = 0; i < row; ++i) {
        for (int j = 0; j < col; ++j) {
            if (source.fill[i][j] == NOT_FILL || source.fill[i][j] == FULL_FILL) {
                result.fill[col - 1 - j][i] = source.fill[i][j];
            } else if (source.fill[i][j] == DIAG_LEFT_UP) {
                result.fill[col - 1 - j][i] = DIAG_RIGHT_UP;
            } else if (source.fill[i][j] == DIAG_RIGHT_UP) {
                result.fill[col - 1 - j][i] = DIAG_RIGHT_DOWN;
            } else if (source.fill[i][j] == DIAG_RIGHT_DOWN) {
                result.fill[col - 1 - j][i] = DIAG_LEFT_DOWN;
            } else if (source.fill[i][j] == DIAG_LEFT_DOWN) {
                result.fill[col - 1 - j][i] = DIAG_LEFT_UP;
            }
        }
    }

    return result;
}

bool fillEqual(FillSquare& source, FillSquare& target)
{
    int sourceRow = source.row;
    int sourceCol = source.col;

    int targetRow = target.row;
    int targetCol = target.col;

    if (sourceRow != targetRow) {
        return false;
    }

    if (sourceCol != targetCol) {
        return false;
    }

    for (int i = 0; i < sourceRow; ++i) {
        for (int j = 0; j < sourceCol; ++j) {
            if (source.fill[i][j] != target.fill[i][j]) {
                return false;
            }
        }
    }

    return true;
}

void initFeasibleFill(vector<FillSquare>& list, FillSquare& fill)
{
    list.push_back(fill);
    FillSquare result = fill;
    for (int i = 0; i < 3; ++i) {
        result = rotate(result);
        bool exist = false;
        for (int j = 0; j < list.size(); ++j) {
            if (fillEqual(list[j], result)) {
                exist = true;
                break;
            }
        }

        if (!exist) {
            list.push_back(result);
        }
    }
}

void unFillCurSquare(FillSquare& square, int xoffset, int yoffset, FillSquare& filled)
{
    int row = square.row;
    int col = square.col;

    for (int i = 0; i < row; ++i) {
        for (int j = 0; j < col; ++j) {
            filled.fill[xoffset + i][yoffset + j] -= square.fill[i][j];
        }
    }
}

void fillCurSquare(FillSquare& square, int xoffset, int yoffset, FillSquare& filled)
{
    int row = square.row;
    int col = square.col;

    for (int i = 0; i < row; ++i) {
        for (int j = 0; j < col; ++j) {
            filled.fill[xoffset + i][yoffset + j] += square.fill[i][j];
        }
    }
}

bool checkCurFillSquare(FillSquare& square, int xoffset, int yoffset, FillSquare& filled)
{
    int row = square.row;
    int col = square.col;

    for (int i = 0; i < row; ++i) {
        for (int j = 0; j < col; ++j) {
            if (filled.fill[i][j] != NOT_FILL &&
                    filled.fill[i][j] != FULL_FILL &&
                    filled.fill[i][j] != DIAG_LEFT_UP &&
                    filled.fill[i][j] != DIAG_RIGHT_DOWN &&
                    filled.fill[i][j] != DIAG_LEFT_DOWN &&
                    filled.fill[i][j] != DIAG_RIGHT_UP) {
                return false;
            }
        }
    }

    return true;
}

bool dfs(int cur, vector<vector<FillSquare>>& fills, FillSquare& filled)
{
    if (cur == n) {
        bool ok = true;
        for (int i = 0; i < m; ++i) {
            for (int j = 0; j < m; ++j) {
                if (filled.fill[i][j] != FULL_FILL) {
                    ok = false;
                    break;
                }
            }
        }

        return ok;
    }

    vector<FillSquare>& curFeasibleFill = fills[cur];
    for (int i = 0; i < m; ++i) {
        for (int j = 0; j < m; ++j) {
            if (filled.fill[i][j] != FULL_FILL) {
                for (int k = 0; k < curFeasibleFill.size(); ++k) {
                    FillSquare& curFill = curFeasibleFill[k];
                    int row = curFill.row;
                    int col = curFill.col;
                    if (i + row > m) continue;
                    if (j + col > m) continue;

                    for (int a = 0; a <= m - row; ++a) {
                        for (int b = 0; b <= m - col; ++b) {
                            fillCurSquare(curFill, a, b, filled);
                            bool ok = checkCurFillSquare(curFill, a, b, filled);
                            if (!ok) {
                                unFillCurSquare(curFill, a, b, filled);
                                continue;
                            }

                            if (dfs(cur + 1, fills, filled)) {
                                return true;
                            }
                            unFillCurSquare(curFill, a, b, filled);
                        }
                    }
                }
            }
        }
    }


    for (int i = 0; i < curFeasibleFill.size(); ++i) {
        FillSquare& curFill = curFeasibleFill[i];
        int row = curFill.row;
        int col = curFill.col;

        for (int j = 0; j <= m - row; ++j) {
            for (int k = 0; k <= m - col; ++k) {
                fillCurSquare(curFill, j, k, filled);
                bool ok = checkCurFillSquare(curFill, j, k, filled);
                if (!ok) {
                    unFillCurSquare(curFill, j, k, filled);
                    continue;
                }

                if (dfs(cur + 1, fills, filled)) {
                    return true;
                }
                unFillCurSquare(curFill, j, k, filled);
            }
        }
    }

    return false;
}

bool solve()
{
    memset(&filled, 0x00, sizeof filled);

    for (int i = 0; i < n; ++i) {
        FillSquare fillSquare;
        memset(&fillSquare, 0x00, sizeof fillSquare);
        scanLineFill(polygons[i], fillSquare);
        initFeasibleFill(puzzles[i], fillSquare);
    }
    
    printPuzzles(puzzles);
    bool ok = dfs(0, puzzles, filled);
    return ok;
}

int main()
{
    fastio();
    #ifndef ONLINE_JUDGE
        ifstream fin("f:\\OJ\\uva_in.txt");
        streambuf* cinback = cin.rdbuf(fin.rdbuf());
    #endif // ONLINE_JUDGE

    int p;
    cin >> p;
    while (p--) {
        cin >> n >> m;
        polygons.clear();
        for (int i = 0; i < n; ++i) {
            int k;
            cin >> k;
            Polygon polygon;
            for (int j = 0; j < k; ++j) {
                int x, y;
                cin >> x >> y;
                polygon.points.push_back((Point){x, y});
            }
            polygons.push_back(polygon);
        }

        bool ans = solve();
        if (ans) {
            cout << "yes";
        } else {
            cout << "no";
        }
    }

    #ifndef ONLINE_JUDGE
        cin.rdbuf(cinback);
    #endif // ONLINE_JUDGE
    return 0;
}
