#include <iostream>
#include <fstream>
#include <string>

using namespace std;

typedef unsigned long long ull;

const int P = 131;
const int MAXN = 1e6 + 5;

ull p[MAXN], ha[MAXN];

void fastio()
{
    ios_base::sync_with_stdio(false);
    cin.tie(NULL);
    cout.tie(NULL);
}

void calHash(const string& s)
{
    int len = s.size();
    p[0] = 1;
    ha[0] = 0;

    for (int i = 0; i < len; ++i) {
        ha[i + 1] = ha[i] * P + s[i];
        p[i + 1] = p[i] * P;
    }
}

ull getHash(int l, int r)
{
    return ha[r] - p[r - l + 1] * ha[l - 1];
}

bool ok(int mid, int len)
{
    ull preh = getHash(1, mid);
    ull sufh = getHash(len - mid + 1, len);
    if (preh != sufh) {
        return false;
    }

    ull h;
    for (int i = 2; i + mid - 1 <= len - 1; ++i) {
        h = getHash(i, i + mid - 1);
        if (h == preh) {
            return true;
        }
    }

    return false;
}

void solve(const string& s)
{
    calHash(s);

    int len = s.size();
    bool flag = false;
    int i;
    for (i = len - 1; i >= 1; --i) {
        if (ok(i, len)) {
            flag = true;
            break;
        }
    }

    if (flag) {
        for (int j = 0; j < i; ++j) {
            cout << s[j];
        }
        cout << endl;
    } else {
        cout << "Just a legend" << endl;
    }

}

int main()
{
    fastio();

    #ifndef ONLINE_JUDGE
        ifstream fin("f:\\OJ\\uva_in.txt");
        streambuf* cinback = cin.rdbuf(fin.rdbuf());
    #endif // ONLINE_JUDGE


    string s;
    cin >> s;
    solve(s);

    #ifndef ONLINE_JUDGE
        cin.rdbuf(cinback);
    #endif // ONLINE_JUDGE

    return 0;
}
