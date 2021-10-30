/**
先计算出文本串和模式串的所有前缀的哈希，然后二分法计算哈希值，并计算次数，看是否满足。
*/
#include <bits/stdc++.h>
 
using namespace std;
 
using ull = unsigned long long;
 
const ull X = 131;
const int N = 100005;
 
ull hashText[N], hashPattern[N], p[N];
 
string text, pattern;
int k;
 
void fastio()
{
    ios_base::sync_with_stdio(false);
    cin.tie(NULL);
    cout.tie(NULL);
}
 
void init()
{
    p[0] = 1;
    for (int i = 1; i < N; ++i) {
        p[i] = p[i - 1] * X;
    }
    
    int textLen = text.length();
    hashText[0] = 0;
    for (int i = 1; i <= textLen; ++i) {
        hashText[i] = hashText[i - 1] * X + text[i - 1];
    }
    
    int patternLen = pattern.length();
    hashPattern[0] = 0;
    for (int i = 1; i <= patternLen; ++i) {
        hashPattern[i] = hashPattern[i - 1] * X + pattern[i - 1];
    } 
}
 
bool check(int len)
{
    int cnt = 0;
    int textLen = text.length();
    for (int i = 1; i + len - 1 <= textLen; ++i) {
        ull textHash = hashText[i + len - 1] - hashText[i - 1] * p[len];
        if (textHash == hashPattern[len]) {
            cnt++;
            if (cnt >= k) {
                return true;
            }
        }
    }
 
    return false;
}
 
string solve()
{   
    init();
    int left = 1, right = pattern.length();
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