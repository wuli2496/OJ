#include <iostream>
#include <fstream>

using namespace std;

template<typename Result>
class AlgoPolicy
{
public:
    ~AlgoPolicy() {}
    virtual Result execute() = 0;
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
