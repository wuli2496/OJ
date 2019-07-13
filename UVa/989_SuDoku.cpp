#include <cstdio>
#include <vector>
#include <cstring>

using namespace std;

const int MAXN = 3600;
const int SLOT = 0;
const int ROW = 1;
const int COL = 2;
const int SUB = 3;
const int N = 9;

class DLX
{
private :
    int n;
    int L[MAXN], R[MAXN], U[MAXN], D[MAXN];
    int col[MAXN], row[MAXN], S[MAXN];
    int ans[MAXN], ansd;
    int sz;

public:
    void init(int n)
    {
        this->n = n;

        for (int i = 0; i <= n; i++) {
            L[i] = i - 1;
            R[i] = i + 1;
            U[i] = i;
            D[i] = i;
        }
        L[0] = n;
        R[n] = 0;
        sz = n + 1;

        memset(S, 0x00, sizeof(S));
    }

    void addRow(int r, vector<int> &columns)
    {
        int first = sz;
        size_t size = columns.size();
        for (size_t i = 0; i < size; i++) {
            int c = columns[i];
            L[sz] = sz - 1;
            R[sz] = sz + 1;
            U[sz] = U[c];
            D[U[c]] = sz;
            D[sz] = c;
            U[c] = sz;
            row[sz] = r;
            col[sz] = c;
            S[c]++;
            sz++;
        }

        L[first] = sz - 1;
        R[sz - 1] = first;
    }

    bool solve(vector<int> &v)
    {
        if (!dfs(0)) return false;

        v.clear();
        for (int i = 0; i < ansd; i++) {
            v.push_back(ans[i]);
        }

        return true;
    }

#define FOR(i, A, s) for (int i = A[s]; i != s; i = A[i])

    void cover(int c)
    {
        L[R[c]] = L[c];
        R[L[c]] = R[c];

        FOR (i, D, c) {
            FOR (j, R, i) {
                S[col[j]]--;
                D[U[j]] = D[j];
                U[D[j]] = U[j];
            }
        }
    }

    void uncover(int c)
    {
        FOR (i, U, c) {
            FOR (j, L, i) {
                S[col[j]]++;
                D[U[j]] = j;
                U[D[j]] = j;
            }
        }

        R[L[c]] = c;
        L[R[c]] = c;
    }

    bool dfs(int dep)
    {
        if (R[0] == 0) {
            ansd = dep;
            return true;
        }

        int c = R[0];
        FOR (i, S, 0) {
            if (S[i] < S[c]) c = i;
        }

        cover(c);
        FOR (i, D, c) {
            ans[dep] = row[i];
            FOR (j, R, i) {
                cover(col[j]);
            }

            if (dfs(dep + 1)) return true;

            FOR (j, L, i) {
                uncover(col[j]);
            }
        }

        uncover(c);
        return false;
    }
};

int encode(int a, int b, int c, int n)
{
    return a * n * n + b * n + c + 1;
}

void decode(int code, int n, int &a, int &b, int &c)
{
    code--;
    c = code % n;
    code /= n;
    b = code % n;
    code /= n;
    a = code;
}

int puzzle[N][N];
DLX solver;
int n, m;

bool read()
{
    if (scanf("%d", &m) != 1) return false;

    n = m * m;
    for (int i = 0; i < n; i++) {
        for (int j = 0; j < n; j++) {
            scanf("%d", &puzzle[i][j]);
        }
    }

    return true;
}

int main()
{
#ifndef ONLINE_JUDGE
    freopen("d:\\OJ\\uva_in.txt", "r", stdin);
#endif

    int cas = 0;

    while (read()) {
        solver.init(4 * n * n);
        for (int r = 0; r < n; r++) {
            for (int c = 0; c < n; c++) {
                if (puzzle[r][c] == 0)
                    for (int v = 0; v < n; v++) {
                        vector<int> columns;
                        columns.push_back(encode(SLOT, r, c, n));
                        columns.push_back(encode(ROW, r, v, n));
                        columns.push_back(encode(COL, c, v, n));
                        columns.push_back(encode(SUB, (r / m) * m + c / m, v, n));
                        solver.addRow(encode(r, c, v, n), columns);
                    }
                else {
                    vector<int> columns;
                    columns.push_back(encode(SLOT, r, c, n));
                    columns.push_back(encode(ROW, r, puzzle[r][c] - 1, n));
                    columns.push_back(encode(COL, c, puzzle[r][c] - 1, n));
                    columns.push_back(encode(SUB, (r / m) * m + c / m, puzzle[r][c] - 1, n));
                    solver.addRow(encode(r, c, puzzle[r][c] - 1, n), columns);
                }
            }
        }

        vector<int> ans;
        if (cas++) printf("\n");
        bool ok = solver.solve(ans);
        if (!ok) {
            printf("NO SOLUTION\n");
            continue;
        }


        for (size_t i = 0, sz = ans.size(); i < sz; i++) {
            int r, c, v;
            decode(ans[i], n, r, c, v);
            puzzle[r][c] = 1 + v;
        }

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (j) printf(" ");
                printf("%d", puzzle[i][j]);
            }
            printf("\n");
        }
    }

    return 0;
}
