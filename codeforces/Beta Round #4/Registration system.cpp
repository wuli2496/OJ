#include <iostream>
#include <fstream>
#include <string>
#include <map>

using namespace std;

class Solution
{
public:
    Solution() {}
    ~Solution() {}

    string solve(const string& name)
    {
        if (nameMap.find(name) == nameMap.end()) {
            nameMap[name] = 1;
            return "OK";
        } else {
            int num = nameMap[name];
            ++nameMap[name];
            return name + to_string(num);
        }
    }

private:
    map<string, int> nameMap;
};

int main()
{
    ios::sync_with_stdio(false);
    cin.tie(nullptr);

#ifndef ONLINE_JUDGE
    ifstream fin("/home/wl/OJ/OJ/UVa/uvain");
    streambuf* cinback = cin.rdbuf(fin.rdbuf());
#endif

    int n;
    while (cin >> n) {
        Solution solution;

        for (int i = 0; i < n; ++i) {
            string name;
            cin >> name;
            string ans = solution.solve(name);
            cout << ans << endl;
        }
    }

#ifndef ONLINE_JUDGE
    cin.rdbuf(cinback);
#endif
}
