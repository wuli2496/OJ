#include<iostream>
#include <fstream>
#include <string>
#include <deque>

using namespace std;

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

    string line;
    while ((getline(cin, line), !cin.eof())) {
        string s;
        bool back = true;
        deque<string> q;
        for (size_t i = 0; i < line.length(); ++i) {
            if (line[i] != '[' && line[i] != ']') {
                s += line[i];
            } else {
                if (back) {
                    q.push_back(s);
                } else {
                    q.push_front(s);
                }

                back = (line[i] == ']');

                s = "";
            }
        }

        back ? q.push_back(s) : q.push_front(s);

        for (auto& s : q) {
            cout << s;
        } 

        cout << endl;
    }

#ifndef ONLINE_JUDGE
    cin.rdbuf(back);
#endif

    return 0;
}