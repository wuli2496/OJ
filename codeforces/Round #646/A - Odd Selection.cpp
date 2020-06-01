#include <iostream>
#include <fstream>
#include <algorithm>

using namespace std;

int main()
{
    ios::sync_with_stdio(false);
    cin.tie(nullptr);

#ifndef ONLINE_JUDGE
    ifstream fin("f:\\OJ\\uva_in.txt");
    streambuf* back = cin.rdbuf(fin.rdbuf());
#endif

    int t;
    cin >> t;
    while (t--) {
        int n, x;
        cin >> n >> x;
        int evenNum = 0;
        int oddNum = 0;
        for (int i = 0; i < n; ++i) {
            int num;
            cin >> num;
            if (num % 2 == 0) {
                ++evenNum;
            } else {
                ++oddNum;
            }
        }

        int select = min(x, oddNum);

        bool flag = false;
        for (int i = 1; i <= select; i += 2) {
            if (x - i <= evenNum) {
                flag = true;
                break;
            }
        }

        if (flag) {
            cout << "Yes" << endl;
        } else {
            cout << "No" << endl;
        }
    }

#ifndef ONLINE_JUDGE
    cin.rdbuf(back);
#endif
    return 0;
}
