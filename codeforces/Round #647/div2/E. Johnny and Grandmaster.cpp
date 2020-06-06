#include <bits/stdc++.h>

using namespace std;

#define forn(i, n) for (int i = 0; i < (int)(n); ++i)
#define for1(i, n) for (int i = 1; i <= (int)(n); ++i)
#define ford(i, n) for (int i = (int)(n) - 1; i >=0; --i)
#define fore(i, s, e) for (int i = (s); i < (e); ++i)
#define sz(x) (int)(x).size()
#define all(x) (x).begin(), (x).end()
#define rall(x) (x).rbegin(), (x).rend()

using LL = long long;

template <typename T> 
istream& operator>>(istream& in, vector<T>& v) 
{
    for (auto& i : v) {
        in >> i;
    }

    return in;
}

template <typename T>
ostream& operator<<(ostream& out, vector<T>& v)
{
    for (auto& i : v) {
        out << i << " ";
    }
    out << endl;

    return out;
}

template <typename T, typename U>
void chkMin(T& a, U b)
{
    if (a > b) {
        a = b;
    }    
}

template <typename T, typename U>
void chkMax(T& a, U b)
{
    if (a < b) {
        a = b;
    }
}

struct IO 
{
#define MAXSIZE (1 << 20)
#define isdigit(x) (x >= '0' && x <= '9')
    
    char buf[MAXSIZE], *p1, *p2;
    char pbuf[MAXSIZE], *pp;

 #if DEBUG
 #else
    IO() : p1(buf), p2(buf), pp(pbuf) {}

    ~IO()
    {
        fwrite(pbuf, 1, pp - pbuf, stdout);
    }
 #endif

    inline char gc()
    {
#if DEBUG
        return getchar();
#endif

        if (p1 == p2) {
            p2 = (p1 = buf) + fread(buf, 1, MAXSIZE, stdin);
        }

        return p1 == p2 ? ' ' : *p1++;
    }

    inline bool blank(char ch)
    {
        return ch == ' ' || ch == '\n' || ch == '\r' || ch == '\t';
    }

    template <typename T>
    inline void read(T& x)
    {
        double tmp = 1;
        bool sign = 0;
        x = 0;

        char ch = gc();
        for (; !isdigit(ch); ch = gc()) {
            if (ch == '-') {
                sign = 1;
            }
        }

        for (; isdigit(ch); ch = gc()) {
            x = x * 10 + (ch - '0');
        }

        if (ch == '.') {
            for (ch = gc(); isdigit(ch); ch = gc()) {
                tmp /= 10.0;
                x += tmp * (ch - '0');
            }
        }

        if (sign) {
            x = -x;
        }
    }

    inline void read(char *s)
    {
        char ch = gc();
        for (; blank(ch); ch = gc());
        for (; !blank(ch); ch = gc()) {
            *s++ = ch;
        }

        *s = 0;
    }

    inline void read(char& c) 
    {
        for (c = gc(); blank(c); c = gc());
    }

    inline void push(const char& c)
    {
        #if DEBUG
            putchar(c);
        #else 
            if (pp - pbuf == MAXSIZE) {
                fwrite(pbuf, 1, MAXSIZE, stdout);
                pp = pbuf;
            }

            *pp++ = c;
        #endif
    }

    template <typename T>
    inline void write(T x)
    {
        if (x < 0) {
            x = -x;
            push('-');
        }

        static T sta[35];
        T top = 0;
        do {
            sta[top++] = x % 10;
            x /= 10;
        } while (x);

        while (top) {
            push(sta[--top] + '0');
        }
    }

    template <typename T>
    inline void write(T x, char lastChar)
    {
        write(x);
        push(lastChar);
    }
} io;

namespace Factor
{
    template<typename T>
    T qpow(T a, T b, T m)
    {
        T c = 1 % m;

        while (b)
        {
            if (b & 1) {
                c = c * a % m;
            }

            a = a * a % m;

            b >>= 1;
        }

        return c;
    }
}

class Solution
{
public:
    Solution(LL n, LL p, const vector<LL>& k)
    {
        this->n = n;
        this->p = p;
        this->k = k;
    }

    LL solve()
    {
        if (p == 1) {
            return n % 2;
        }
        constexpr LL MOD = 1e9+7;
        LL sum = 0;
        LL ans = 0;

        sort(all(k), greater<LL>());

        bool flag = false;
        forn(i, n) {
            if (i != 0 && k[i - 1] != k[i]) {
                LL minVal = 20;
                chkMin(minVal, k[i - 1] - k[i]);
                forn(j, minVal) {
                    if (sum > n) {
                        flag = true;
                        break;
                    }

                    sum *= p;
                }
                
                ans = ans * Factor::qpow(p, k[i - 1] - k[i], MOD) % MOD;
            }

            if (!flag) {
                if (sum == 0) {
                    ++sum;
                    ans = (ans + 1) % MOD;
                } else {
                    --sum;
                    ans = (ans + MOD - 1) % MOD;
                }
            } else {
                ans = (ans + MOD - 1) % MOD;
            }
        }

        ans = ans * Factor::qpow(p, k[n - 1], MOD) % MOD;

        return ans;
    }

private:
    LL n, p;
    vector<LL> k;
};

int main()
{
    ios::sync_with_stdio(false);
    cin.tie(nullptr);

#ifndef ONLINE_JUDGE
    ifstream fin("/home/wl/OJ/OJ/codeforces/codeforces_in");
    streambuf* cinback = cin.rdbuf(fin.rdbuf());
#endif
    
    int t;
    cin >> t;
    while (t--) {
        LL n, p;
        cin >> n >> p;
        vector<LL> k(n);
        forn(i, n) {
            cin >> k[i];
        }

        Solution solution(n, p, k);
        LL ans = solution.solve();
        cout << ans << endl;
    }

#ifndef ONLINE_JUDGE
    cin.rdbuf(cinback);
#endif
}
