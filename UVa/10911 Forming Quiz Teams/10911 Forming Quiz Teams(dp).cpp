#include <cstdio>
#include <algorithm>
#include <cstring>
#include <cmath>

using namespace std;

int N;
double dist[20][20], memo[1 << 16];

double matching(int bitmask)
{
    if (memo[bitmask] > -0.5) {
        return memo[bitmask];
    }

    if (bitmask == (1 << 2 * N) - 1) {
        return memo[bitmask] = 0;
    }

    double matching_value = 32767 * 32767;
    for (int i = 0; i < 2 * N; i++) {
        if (!(bitmask & (1 << i))) {
            for (int j = i + 1; j < 2 * N; j++) {
                if (!(bitmask & (1 << j))) {
                    matching_value = min(matching_value, dist[i][j] + matching(bitmask | (1 << i) | (1 << j)));
                }
            }
        }
    }

    return memo[bitmask] = matching_value;

}

int main()
{
    char line[1000], name[30];
    int caseNo = 1, x[20], y[20];

    //freopen("f:\\OJ\\uva_in.txt", "r", stdin);
    while (sscanf(gets(line), "%d", &N), N) {
        for (int i = 0; i < 2 * N; i++){
            sscanf(gets(line), "%s %d %d", name, &x[i], &y[i]);
        }

        for (int i = 0; i < 2 * N; i++) {
            for (int j = 0; j < 2 * N; j++) {
                dist[i][j] = sqrt((double)(x[i] - x[j]) * (x[i] - x[j]) + (y[i] - y[j]) * (y[i] - y[j]));
            }
        }

        memset(memo, -1, sizeof memo);
        printf("Case %d: %.2lf\n", caseNo++, matching(0));
    }

    return 0;
}
