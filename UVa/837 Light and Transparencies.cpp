#include <cstdio>
#include <vector>
#include <algorithm>
#include <cmath>
 
using namespace std;
 
const double EPS = 1e-6;
const int INF = 0x3f3f3f3f;
 
struct Line
{
    double x1, x2, trans;
    bool operator < (const Line &other) const
    {
        if (fabs(x1 - other.x1) > EPS) return x1 < other.x1;
 
        return x2 < other.x2;
    }
};
 
 
vector<double> vPoint;
vector<Line> vLine;
 
void input();
void solve();
 
int main()
{
#ifndef ONLINE_JUDGE
    freopen("e:\\uva_in.txt", "r", stdin);
#endif
 
    int t;
    scanf("%d", &t);
    while (t--) {
        input();
        solve();
        if (t) printf("\n");
    }
    return 0;
}
 
void input()
{
    int n;
    Line line;
 
    scanf("%d", &n);
    vPoint.clear();
    vLine.clear();
    for (int i = 0; i < n; i++) {
        scanf("%lf%*lf%lf%*lf%lf", &(line.x1), &(line.x2), &(line.trans));
        //printf("%lf %lf %lf\n", line.x1, line.x2, line.trans);
        if (line.x1 > line.x2) {
            swap(line.x1, line.x2);
        }
        vPoint.push_back(line.x1);;
        vPoint.push_back(line.x2);
        vLine.push_back(line);
    }
 
    sort(vPoint.begin(), vPoint.end());
    sort(vLine.begin(), vLine.end());
 
    /*
    printf("size=%d\n", vLine.size());
    for (size_t i = 0; i < vLine.size(); i++) {
        printf("%.3lf %.3lf\n", vLine[i].x1, vLine[i].x2);
    }
    */
}
 
void solve()
{
    vector<Line> ans;
    Line line;
    line.x1 = -INF, line.x2 = vPoint.front(), line.trans = 1.0;
 
    ans.push_back(line);
    for (int i = 0; i < vPoint.size() - 1; i++) {
        double trans = 1.0;
        for (int j = 0; j < vLine.size(); j++) {
            if (vPoint[i] >= vLine[j].x1 && vPoint[i] <= vLine[j].x2 &&
                    vPoint[i + 1] >= vLine[j].x1 && vPoint[i + 1] <= vLine[j].x2) {
                trans *= vLine[j].trans;
            }
        }
        line.x1 = vPoint[i], line.x2 = vPoint[i + 1], line.trans = trans;
        ans.push_back(line);
    }
 
    line.x1 = vPoint.back(), line.x2 = INF, line.trans = 1.0;
    ans.push_back(line);
 
    printf("%d\n", ans.size());
    printf("-inf %.3lf %.3lf\n", ans[0].x2, 1.0);
    for (int i = 1; i < ans.size() - 1; i++) {
        printf("%.3lf %.3lf %.3lf\n", ans[i].x1, ans[i].x2, ans[i].trans);
    }
    printf("%.3lf +inf %.3lf\n", ans[ans.size() - 1].x1, 1.0);
}