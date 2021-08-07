#include <iostream>
#include <fstream>
#include <algorithm>

using namespace std;

const int N = 30;

void fastio()
{
    ios_base::sync_with_stdio(false);
    cin.tie(NULL);
    cout.tie(NULL);
}

int device[N];
int swither[N];

int main()
{
    fastio();
    #ifndef ONLINE_JUDGE
        ifstream fin("f:\\OJ\\uva_in.txt");
        streambuf* cinback = cin.rdbuf(fin.rdbuf());
    #endif // ONLINE_JUDGE


    int n, m, c;
    int caseNo = 1;
    while (cin >> n >> m >> c) {
        if (n == 0 && m == 0 && c ==0) {
            break;
        }

        for (int i = 1; i <= n; ++i) {
            cin >> device[i];
            swither[i] = 1;
        }

        cout << "Sequence " << caseNo++ << endl;
        int sum = 0;
        int maxSum = 0;
        bool blown = false;
        for (int i = 1; i <= m; ++i) {
            int num;
            cin >> num;
            sum += device[num] * swither[num];
            if (sum > c) {
                blown = true;
            }

            maxSum = max(maxSum, sum);
            swither[num] = -swither[num];
        }

        if (!blown) {
            cout << "Fuse was not blown." << endl << "Maximal power consumption was " << maxSum << " amperes." << endl;
        } else {
            cout << "Fuse was blown." << endl;
        }

        cout << endl;
    }
    #ifndef ONLINE_JUDGE
        cin.rdbuf(cinback);
    #endif // ONLINE_JUDGE
    return 0;
}
