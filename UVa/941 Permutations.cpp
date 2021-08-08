#include <iostream>
#include <fstream>
#include <algorithm>
#include <string>
#include <vector>

using namespace std;

typedef long long ll;

template<typename T>
void print(ostream& os, vector<T>& v)
{
    for (auto& e : v) {
        os << e << ' ';
    }
    os << endl;
}

void fastio()
{
    ios_base::sync_with_stdio(false);
    cin.tie(NULL);
    cout.tie(NULL);
}

vector<int> convertFactorial(ll n, int len)
{
    vector<int> ans;
    ll num = 1;
    while (n != 0) {
        int remaider = n % num;
        ans.push_back(remaider);
        n = n / num;
        ++num;
    }

    if (ans.size() < len) {
        for (int i = ans.size(); i < len; ++i) {
            ans.push_back(0);
        }
    }
    reverse(ans.begin(), ans.end());

    return ans;
}

char getUnusedCh(const string& src, int pos, vector<bool>& unused)
{
    int cnt = 0;
    for (size_t i = 0; i < unused.size(); ++i) {
        if (!unused[i]) {
           if (cnt == pos) {
               unused[i] = true;
               return src[i];
           }
           ++cnt;
        }
    }
    return ' ';
}

string getPermutation(const string& src, const vector<int>& factorial)
{
    string ans;
    vector<bool> unused(src.length(), false);
    for (vector<int>::const_iterator it = factorial.begin(); it != factorial.end(); it++) {
        int pos = *it;
        char ch = getUnusedCh(src, pos, unused);
        ans.push_back(ch);
    }

    return ans;
}

int main()
{
    fastio();
    #ifndef ONLINE_JUDGE
        ifstream fin("f:\\OJ\\uva_in.txt");
        streambuf* cinback = cin.rdbuf(fin.rdbuf());

        ofstream fout("f:\\OJ\\uva_out.txt");
        streambuf* coutback = cout.rdbuf(fout.rdbuf());
    #endif // ONLINE_JUDGE


    int cases;
    cin >> cases;
    for (int i = 0; i < cases; ++i) {
       string s;
       ll n;
       cin >> s >> n;
       sort(s.begin(), s.end());
       vector<int> factorialNum = convertFactorial(n, s.length());
       string ans = getPermutation(s, factorialNum);
       cout << ans << endl;
    }

    #ifndef ONLINE_JUDGE
        cin.rdbuf(cinback);
        cout.rdbuf(coutback);
    #endif // ONLINE_JUDGE

    return 0;
}
