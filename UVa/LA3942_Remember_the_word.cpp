#include <iostream>
#include <cstring>
#include <string>
#include <cstdio>
#include <exception>

using namespace std;

const int N = 400010;
const int MAX_CHILD_NUM = 26;
const int WORD_LEN = 110;
const int MOD = 20071027;

class Trie
{
public:
    void init()
    {
        memset(ch, 0x00, sizeof(ch));
        memset(val, 0x00, sizeof(val));
        memset(dp, 0xff, sizeof(dp));
        cnt = 0;
    }

    void insert(string s)
    {
        int u = 0;
        for (int i = 0; i < s.length(); i++)
        {
            int idx = s[i] - 'a';
            if (!ch[u][idx])
            {
                cnt++;
                ch[u][idx] = cnt;
                val[cnt] = 0;
            }
            u = ch[u][idx];
        }
        val[u] = 1;
    }

    int find(string& s)
    {
        int len = s.length();
        for (int i = 0; i <= len; i++)
        {
            cal(s, len - i);
        }

        return dp[0];
    }

private:
    void cal(string& s, int start)
    {
        int u = 0;
        int len = s.length();
        int& ans = dp[start];
        if (start == len)
        {
            ans = 1;
            return;
        }

        ans = 0;
        for (int i = start; i < len; i++)
        {
            int idx = s[i] - 'a';
            u = ch[u][idx];
            if (u == 0) break;
            if (val[u])
            {
                ans += dp[i + 1];
                ans %= MOD;
            }
        }
    }
private:
    int ch[N][MAX_CHILD_NUM];
    int val[N];
    int cnt;
    int dp[N];
};

char buf[N], tmp[WORD_LEN];
Trie trie;

int main()
{
#ifndef ONLINE_JUDGE
    freopen("d:\\program\\clion\\spoj.txt", "r", stdin);
#endif


    string s;
    int cas = 1;
    while (scanf("%s", buf) == 1)
    {
        trie.init();
        int n;
        scanf("%d", &n);
        for (int i = 0; i < n; i++)
        {
            string word;
            scanf("%s", tmp);
            word = tmp;
            trie.insert(word);
        }
        s = buf;
        int ans = trie.find(s);
        printf("Case %d: %d\n", cas++, ans);
    }

    return 0;
}
