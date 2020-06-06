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
        fwrite(pbuf, 1, pp - - pbuf, stdout);
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

int main()
{
    ios::sync_with_stdio(false);
    cin.tie(nullptr);

#ifndef ONLINE_JUDGE
    ifstream fin("/home/wl/OJ/OJ/codeforces/codeforces_in");
    streambuf* cinback = cin.rdbuf(fin.rdbuf());
#endif

#ifndef ONLINE_JUDGE
    cin.rdbuf(cinback);
#endif
}
