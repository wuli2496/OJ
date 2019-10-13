#include <iostream>
#include <fstream>

using namespace std;

class AlgoPolicy
{
public:
    ~AlgoPolicy() {}
    virtual void execute() = 0;
};

int main()
{
    ios::sync_with_stdio(false);
    cin.tie(nullptr);

#ifndef ONLINE_JUDGE
    ifstream fin("/home/wl/OJ/OJ/UVa/uvain");
    streambuf* cinback = cin.rdbuf(fin.rdbuf());
#endif

#ifndef ONLINE_JUDGEL
    cin.rdbuf(cinback);
#endif
}
