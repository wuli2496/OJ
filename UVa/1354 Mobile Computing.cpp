#include <iostream>
#include <fstream>
#include <vector>
#include <algorithm>
#include <iomanip>

using namespace std;

const int MAXN = 6;

struct Tree
{
    double l, r;
    Tree() : l(0), r(0) {}
};

int s;
double r, w[MAXN], sum[1 << MAXN];
vector<Tree> trees[1 << MAXN];
bool visited[1 << MAXN];

void dfs(int subset)
{
    if (visited[subset]) return;
    visited[subset] = true;

    bool hasChild = false;
    for (int left = subset & (subset - 1); left; left = (subset & (left - 1))) {
        hasChild = true;
        int right = subset ^ left;
        double lLen = sum[right] / sum[subset];
        double rLen = sum[left] / sum[subset];
        dfs(left);
        dfs(right);
        for (size_t i = 0; i < trees[left].size(); ++i) {
            for (size_t j = 0; j < trees[right].size(); ++j) {
                Tree tree;
                tree.l = max(trees[left][i].l + lLen, trees[right][j].l - rLen);
                tree.r = max(trees[left][i].r - lLen, trees[right][j].r + rLen);
                if (tree.l + tree.r < r) {
                    trees[subset].push_back(tree);
                }
            }
        }
    }

    if (!hasChild) {
        trees[subset].push_back(Tree());
    }
}

void fastio()
{
    ios_base::sync_with_stdio(false);
    cin.tie(NULL);
    cout.tie(NULL);
}

int main()
{
    fastio();
    #ifndef ONLINE_JUDGE
        ifstream fin("f:\\OJ\\uva_in.txt");
        streambuf* cinback = cin.rdbuf(fin.rdbuf());
    #endif // ONLINE_JUDGE

    int cases;
    cin >> cases;
    while (cases--) {
        cin >> r >> s;
        for (int i = 0; i < s; ++i) {
            cin >> w[i];
        }

        for (int i = 0; i < (1 << s); ++i) {
            sum[i] = 0;
            trees[i].clear();
            for (int j = 0; j < s; ++j) {
                if (i & (1 << j)) {
                    sum[i] += w[j];
                }
            }
        }

        int root = (1 << s) - 1;
        fill(visited, visited + root + 1, false);
        dfs(root);

        double ans = -1;
        for (size_t i = 0; i < trees[root].size(); ++i) {
            ans = max(ans, trees[root][i].l + trees[root][i].r);
        }

        cout << setiosflags(ios::fixed) << setprecision(10) << ans << endl;
    }


    #ifndef ONLINE_JUDGE
        cin.rdbuf(cinback);
    #endif // ONLINE_JUDGE
    return 0;
}
