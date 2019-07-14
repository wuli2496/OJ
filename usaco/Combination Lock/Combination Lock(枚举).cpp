/*
ID: wuli2491
TASK: combo
LANG: C++                 
*/
//枚举
#include <iostream>
#include <fstream>
#include <cmath>
#include <cstring>

using namespace std;

const int MAXN = 3;

struct Lock
{
    int data[MAXN];
    
    Lock()
    {
        memset(data, 0x00, sizeof(data));
    }
};

class Solution
{
public:
    Solution(int n, const Lock& farmer, const Lock& master);
    
    int execute();
    
private:
    bool close(int a, int b);
    bool isCloseEnough(const Lock& s, const Lock& other);
    
private:
    int m_n;
    Lock m_farmer, m_master;
};

Solution::Solution(int n, const Lock& farmer, const Lock& master)
{
    m_n = n;
    m_farmer = farmer;
    m_master = master;
}

int Solution::execute()
{
    int total = 0;
    
    for (int i = 1; i <= m_n; ++i)
    {
        for (int j = 1; j <= m_n; ++j)
        {
            for (int k = 1; k <= m_n; ++k)
            {
                Lock curLock;
                curLock.data[0] = i; curLock.data[1] = j; curLock.data[2] = k;
                if (isCloseEnough(m_farmer, curLock) || isCloseEnough(m_master, curLock))
                {
                    ++total;
                }
            }
        }
    }
    
    return total;
}

bool Solution::close(int a, int b)
{
    if (abs(a - b) <= 2)
    {
        return true;
    }
    
    if (abs(a - b) >= m_n - 2) 
    {
        return true;
    }
    
    return false;
}

bool Solution::isCloseEnough(const Lock& s, const Lock& other)
{
    for (int i = 0; i < MAXN; ++i)
    {
        if (!close(s.data[i], other.data[i]))
        {
            return false;
        }
    }
    
    return true;
}

int main(int argc, char** argv) 
{
    ifstream fin("combo.in");
    ofstream fout("combo.out");
    cin.rdbuf(fin.rdbuf());
    cout.rdbuf(fout.rdbuf());
    
    int n;
    cin >> n;
    
    Lock farmer, master;
    cin >> farmer.data[0] >> farmer.data[1] >> farmer.data[2];
    cin >> master.data[0] >> master.data[1] >> master.data[2];
    
    Solution solution(n, farmer, master);
    int ans = solution.execute();
    
    cout << ans << endl;
    
    return 0;
}
