#include <iostream>
#include <fstream>
#include <memory>
#include <vector>
#include <algorithm>

using namespace std;

struct Song
{
    int id;
    int len;
    double frequency;

    Song()
    {
        id = 0;
        len = 0;
        frequency = 0;
    }

    bool operator<(const Song& other) const
    {
        return len / frequency < other.len / other.frequency;
    }
};

class AlgoPolicy
{
public:
    virtual ~AlgoPolicy(){}
    virtual int execute() = 0;
};


class GreedyAlgo : public AlgoPolicy
{
public:
    GreedyAlgo(vector<Song>& songVec, int pos)
    {
        songs = songVec;
        p = pos;
    }
    
    int execute() override
    {
        sort(songs.begin(), songs.end());
        return songs[p - 1].id;
    }

private:
    vector<Song> songs;
    int p;
};

AlgoPolicy* makeAlgo(const string& s, vector<Song>& songVec, int pos)
{
    if (s == "greedy")
    {
        return new GreedyAlgo(songVec, pos);
    }

    return nullptr;
}

class Solution
{
public:
    Solution(AlgoPolicy* algo)
    {
        pimpl.reset(algo);
    }

    int run()
    {
        return pimpl->execute();
    }

private:
    shared_ptr<AlgoPolicy> pimpl;
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
    while (cin >> n)
    {
        vector<Song> songs(n);
        for (int i = 0; i < n; ++i)
        {
            cin >> songs[i].id >> songs[i].len >> songs[i].frequency;
        }
        int pos;
        cin >> pos;
        AlgoPolicy* algo = makeAlgo("greedy", songs, pos);
        Solution solution(algo);
        int ans = solution.run();
        cout << ans << endl;
    }
#ifndef ONLINE_JUDGE
    cin.rdbuf(cinback);
#endif

    return 0;
}
