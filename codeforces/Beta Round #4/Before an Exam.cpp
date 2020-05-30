#include <iostream>
#include <fstream>
#include <vector>

using namespace std;


int main()
{
    ios::sync_with_stdio(false);
    cin.tie(nullptr);

#ifndef ONLINE_JUDGE
    ifstream fin("/home/wl/OJ/OJ/UVa/uvain");
    streambuf* cinback = cin.rdbuf(fin.rdbuf());
#endif

    int d, sumTime;
    while (cin >> d >> sumTime) {
        vector<int> minTime(d), maxTime(d);
        
        int totalMinTime = 0, totalMaxTime = 0;
        for (int i = 0; i < d; ++i) {
            cin >> minTime[i] >> maxTime[i];
            totalMinTime += minTime[i];
            totalMaxTime += maxTime[i];
        }

        if (totalMinTime > sumTime || totalMaxTime < sumTime) {
            cout << "NO" << endl;
            continue;
        }

        cout << "YES" << endl;
        int remainderTime = sumTime - totalMinTime;
        bool first = true;
        for (int i = 0; i < d; ++i) {
            int time = 0;
            if (remainderTime >= maxTime[i] - minTime[i]) {
                remainderTime -= maxTime[i] - minTime[i];
                time = maxTime[i];
            } else {
                time = remainderTime + minTime[i];
                remainderTime = 0;
            }

            if (first) {
                first = false;
            } else {
                cout << " ";
            }
            cout << time;
        }
        cout << endl;
    }

#ifndef ONLINE_JUDGE
    cin.rdbuf(cinback);
#endif
}
