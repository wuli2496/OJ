#include <iostream>
#include <fstream>
#include <vector>
#include <algorithm>
#include <climits>

using namespace std;

class Solution
{
public:
    Solution(vector<int> v)
    {
        m_hills = v;
    }
    
    int solve()
    {
        sort(m_hills.begin(), m_hills.end());

        int minHill = *min_element(m_hills.begin(), m_hills.end());
        int maxHill = *max_element(m_hills.begin(), m_hills.end());

        int ans = INT_MAX;
        for (int i = minHill; i <= maxHill; ++i)
        {
            int sum = 0;
            for (int j = 0; j < m_hills.size(); ++j)
            {
                if (m_hills[j] < i)
                {
                    sum += (m_hills[j] - i) * (m_hills[j] - i);
                }
                else if (m_hills[j] - i > 17)
                {
                    sum += (m_hills[j] - i - 17) * (m_hills[j] - i - 17);
                }
            }

            ans = min(sum, ans);
        }

        return ans;
    }
private:
    vector<int> m_hills;
};

int main()
{
    ifstream fin("skidesign.in");
    ofstream fout("skidesign.out");

#ifndef ONLINE_JUDGE
    streambuf* backfin = cin.rdbuf(fin.rdbuf());
    streambuf* backfout = cout.rdbuf(fout.rdbuf());
#endif
    int n;
    vector<int> hills;
    cin >> n;
    for (int i = 0; i < n; ++i)
    {
        int hill;
        cin >> hill;
        hills.push_back(hill);
    }

    Solution solution(hills);
    int ans = solution.solve();
    cout << ans << endl;
#ifndef ONLINE_JUDGE
    cin.rdbuf(backfin);
    cout.rdbuf(backfout);
#endif
    return 0;
}
