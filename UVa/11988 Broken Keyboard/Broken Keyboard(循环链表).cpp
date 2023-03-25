#include<iostream>
#include <fstream>
#include <string>
#include <deque>
#include <cstring>

using namespace std;

const int MAXN = 100000 + 10;
char s[MAXN];
int successor[MAXN];

void fastio()
{
    ios_base::sync_with_stdio(false);
    cin.tie(nullptr);
    cout.tie(nullptr);
}

int main()
{
    fastio();

#ifndef ONLINE_JUDGE
    ifstream fin("f:\\OJ\\uva_in.txt");
    streambuf* back = cin.rdbuf(fin.rdbuf());
#endif

    while (cin >> s + 1) {
        int cur = 0;
        int tail = 0;
        successor[0] = 0;
        int len = strlen(s + 1);

        for (int i = 1; i <= len; ++i) {
            if (s[i] == '[') {
                cur = 0;
            } else if (s[i] == ']'){
                cur = tail;
            } else {
                successor[i] = successor[cur];
                successor[cur] = i;
                cur = i;
                if (successor[cur] == 0) {
                    tail = cur;
                }
            }
        }

        for (int i = successor[0]; i; i = successor[i]) {
            cout << s[i];
        }
        cout << endl;
    }

#ifndef ONLINE_JUDGE
    cin.rdbuf(back);
#endif

    return 0;
}