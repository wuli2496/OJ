/*
ID: wuli2491
TASK: combo
LANG: C++                 
*/
//计数原理 
#include <iostream>
#include <fstream>
#include <cmath>
#include <cstring>

using namespace std;

const int MAXN = 3;
const int N = 5;

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
    int calRepeatNum(const Lock& farmer, const Lock& master, int pos);
    
private:
    int m_n;
    Lock m_farmer, m_master;
};

int main()
{
    ifstream fin("combo.in");
    ofstream fout("combo.out");
    
    int n;
    fin >> n;
    
    Lock farmer, master;
    fin >> farmer.data[0] >> farmer.data[1] >> farmer.data[2];
    fin >> master.data[0] >> master.data[1] >> master.data[2];
    
    Solution solution(n, farmer, master);
    int ans = solution.execute();
    
    fout << ans << endl;
    
    return 0;
}

Solution::Solution(int n, const Lock& farmer, const Lock& master)
{
    m_n = n;
    m_farmer = farmer;
    m_master = master;
}

int Solution::execute()
{
    if (m_n <= N)
    {
        return m_n * m_n * m_n;
    }
    
    int total = N * N * N;
    total *= 2;
    
    int repeat = 1;
    
    for (int i = 0; i < MAXN; ++i)
    {
        repeat *= calRepeatNum(m_farmer, m_master, i);
    }
    
    total -= repeat;
    
    return total;
}

int Solution::calRepeatNum(const Lock& farmer, const Lock& master, int pos)
{
    int farmerCnt = farmer.data[pos];
    int masterCnt = master.data[pos];
    
    int repeatNum = 0;
    int temp = abs(farmerCnt - masterCnt);
    if (temp < N)
    {
        repeatNum += N - temp;
    }
    
    temp = m_n - abs(farmerCnt - masterCnt);
    if (temp < N)
    {
        repeatNum += N - temp;
    }
    
    return repeatNum;
}