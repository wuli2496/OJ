#include <cstdio>
#include <string>
#include <iostream>
#include <vector>
#include <utility>
#include <map>
#include <algorithm>
#include <fstream>

using namespace std;


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

    int h, u, d, f;
    double dist;
    while (cin >> h >> u >> d >> f) {
        if (h == 0) {
            break;
        }

        int day = 1;
        double up = u;
        dist = 0;
        while (1) {
            if (up > 0) {
                dist += up;
            }

            if (dist > h) {
                cout << "success on day " << day << endl;
                break;
            }
            else if (dist - d < 0) {
                cout << "failure on day " << day << endl;
                break;
            } else {
                dist -= d;
                ++day;
                up -= u * f / 100.0;
            }
        }
    }

    #ifndef ONLINE_JUDGE
        cin.rdbuf(cinback);
    #endif // ONLINE_JUDGE
    return 0;
}
