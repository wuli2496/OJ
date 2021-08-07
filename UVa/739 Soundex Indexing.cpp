#include <iostream>
#include <fstream>
#include <algorithm>
#include <string>
#include <iomanip>

using namespace std;

const int N = 26;
const char chMap[N] = {0, 1, 2, 3, 0, 1, 2, 0, 0, 2, 2, 4, 5, 5, 0, 1, 2, 6, 2, 3, 0, 1, 0, 2, 0, 2};

void fastio()
{
    ios_base::sync_with_stdio(false);
    cin.tie(NULL);
    cout.tie(NULL);
}

void printLine(string key, string name) 
{
    int count = 9;
    string ans;
    ans.append(count, ' ');
    cout << ans;
    cout << key;
    count = 25 - key.length();
    ans.clear();
    ans.append(count, ' ');
    cout << ans;
    cout << name << endl;
}

void printFooter() 
{
    int count = 19;
    string ans;
    ans.append(count, ' ');
    cout << ans;
    cout << "END OF OUTPUT" << endl;
}

int main()
{
    fastio();
    #ifndef ONLINE_JUDGE
        ifstream fin("f:\\OJ\\uva_in.txt");
        streambuf* cinback = cin.rdbuf(fin.rdbuf());
    #endif // ONLINE_JUDGE

    string s;
    printLine("NAME", "SOUNDEX CODE");
    while (cin >> s) {
        string ans;
        ans.push_back(s[0]);
        int pre = chMap[s[0] - 'A'];
        for (size_t i = 1; i < s.length(); ++i) {
            int index = s[i] - 'A';
            if (chMap[index] == 0) {
                pre = chMap[index];
                continue;
            }
            
            if (pre == chMap[index]) {
                continue;
            }
            
            ans.push_back('0' + chMap[index]);
            pre = chMap[index];
        }
        
        if (ans.length() < 4) {
            ans.append(4 - ans.length(), '0');
        } else if (ans.length() > 4) {
            ans = ans.substr(0, 4);
        }
        
        
        printLine(s, ans);
        
    }
    printFooter();
    
    #ifndef ONLINE_JUDGE
        cin.rdbuf(cinback);
    #endif // ONLINE_JUDGE
    return 0;
}
