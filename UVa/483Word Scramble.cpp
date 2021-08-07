#include <cstdio>
#include <string>
#include <iostream>
#include <vector>
#include <utility>
#include <map>
#include <algorithm>
#include <fstream>

using namespace std;


void fastio()
{
    ios_base::sync_with_stdio(false);
    cin.tie(NULL);
    cout.tie(NULL);
}

int main()
{

    fastio();
    #ifndef ONLINE_JUDGE
        ifstream fin("f:\\OJ\\uva_in.txt");
        streambuf* cinback = cin.rdbuf(fin.rdbuf());
    #endif // ONLINE_JUDGE

    string s;
    while (getline(cin, s)) {
        int pre = 0;
        for (int i = 0; i < s.size(); ++i) {
            if (s[i] == ' ' || i == s.size() - 1) {
                int endIndex = i;
                if (s[i] == ' ') {
                    endIndex = i - 1;
                }
                //cout << "endIndex:" << endIndex << " pre:" << pre << endl;
                if (endIndex - pre > 0) {
                    for (int j = pre; j <= (endIndex + pre) / 2; ++j) {
                        char tmp = s[j];
                        s[j] = s[endIndex + pre - j];
                        s[endIndex + pre - j] = tmp;
                    }
                }
                pre = i + 1;
            }
        }
        cout << s << endl;
    }

    #ifndef ONLINE_JUDGE
        cin.rdbuf(cinback);
    #endif // ONLINE_JUDGE
    return 0;
}
