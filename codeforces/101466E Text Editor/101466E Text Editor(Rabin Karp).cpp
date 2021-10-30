/**
二分法中使用rolling Rabin Karp计算满足条件次数。
*/
#include <bits/stdc++.h>

using namespace std;

using ull = unsigned long long;

const ull X = 61;


string text, pattern;
int k;

void fastio()
{
    ios_base::sync_with_stdio(false);
    cin.tie(NULL);
    cout.tie(NULL);
}

ull init(int k)
{
    ull xp = 1;
    for (int i = 0; i < k - 1; ++i) {
        xp = xp * X;
    }

    return xp;
}

ull calHash(const string& str, int k)
{
    ull result = 0;
    for (int i = 0; i < k; ++i) {
        result = result * X + str[i];
    }

    return result;
}

bool checkStr(const string& text, const string& pattern, int start, int k)
{
    /*
    for (int i = 0; i < k; ++i) {
        if (text[start + i] != pattern[i]) {
            return false;
        }
    }
    */

    return true;
}

bool check(int len)
{
    ull xp = init(len);
    ull patternHash = calHash(pattern, len);
    ull textHash = calHash(text, len);

    int cnt = 0;
    if (textHash == patternHash) {
        cnt++;
    }

    for (int i = len; i < text.size(); ++i) {
        textHash = (textHash - text[i - len] * xp) * X + text[i];
        if (textHash == patternHash) {
            cnt++;
            if (cnt >= k) {
                return true;
            }
        }
    }

    return cnt >= k;
}

string solve()
{
    int left = 1, right = pattern.size();
    int result = 0;

    while (left <= right) {
        int mid = (left + right) >> 1;
        if (check(mid)) {
            result = mid;
            left = mid + 1;
        } else {
            right = mid - 1;
        }
    }

    if (result == 0) {
        return "IMPOSSIBLE";
    }

    return pattern.substr(0, result);

}

int main()
{
    fastio();
    #ifndef ONLINE_JUDGE
        ifstream fin("f:\\OJ\\uva_in.txt");
        streambuf* cinback = cin.rdbuf(fin.rdbuf());
    #endif // ONLINE_JUDGE

    string kstr;
    getline(cin, text);
    getline(cin, pattern);
    getline(cin, kstr);

    k = atoi(kstr.c_str());

    cout << solve() << endl;

    #ifndef ONLINE_JUDGE
        cin.rdbuf(cinback);
    #endif // ONLINE_JUDGE
    return 0;
}
