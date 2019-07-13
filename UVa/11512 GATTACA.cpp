#include <iostream>
#include <string>
#include <fstream>
#include <vector>
#include <climits>
#include <algorithm>
#include <cstring>
#include <set>
#include <iterator>

using namespace std;

const int MAXN = 100100;
const int N = 256;
const int M = 360;

int str[MAXN];

class SuffixArray
{
public:
    SuffixArray(int* pa, int n);
    ~SuffixArray();
    
    void buildSa();
    void buildHeight();
    
    const int* getSa();
    const int* getHeight();
private:
    int *m_prank;
    int *m_pheight;
    int *m_psa;
    int m_n;
    int* m_a;
};

SuffixArray::SuffixArray(int* pa, int n)
    :m_prank(NULL), m_pheight(NULL),  m_psa(NULL)
{
    m_a = pa;
    m_n = n;
}

SuffixArray::~SuffixArray()
{
    delete []m_prank;
    delete []m_psa;
    delete []m_pheight;
}

void SuffixArray::buildSa()
{
    m_prank = new int[m_n];
    int n = max(M, m_n);
    int* tmp = new int[m_n];
    int* c = new int[n];
    m_psa = new int[m_n];
    m_pheight = new int[m_n];
    
    memset(c, 0, sizeof(int) * n);
    for (int i = 0; i < m_n; i++)
    {
        m_prank[i] = m_a[i];
        c[m_prank[i]]++;
    }
    
    for (int i = 1; i < n; i++)
    {
        c[i] += c[i - 1];
    }
    
    for (int i = m_n - 1; i >= 0; i--)
    {
        m_psa[--c[m_prank[i]]] = i;
    }
    
    int m =  m_n;
    for (int i = 1; i <= m_n; i <<= 1) 
    {
        int p = 0;
        for (int j = m_n - i; j < m_n; j++)
        {
            tmp[p++] = j;
        }
        for (int j = 0; j < m_n; j++)
        {
            if (m_psa[j] >= i)
            {
                tmp[p++] = m_psa[j] - i;
            }
        }
        
        memset(c, 0, sizeof(int) * n);
        for (int j = 0; j < m_n; j++)
        {
            c[m_prank[tmp[j]]]++;
        }
        
        for (int j = 1; j < n; j++)
        {
            c[j] += c[j - 1];
        }
        
        
        for (int j = m_n - 1; j >= 0; j--)
        {
            m_psa[--c[m_prank[tmp[j]]]] = tmp[j];
        }
        
        swap(m_prank, tmp);
        
        m_prank[m_psa[0]] = 0;
        
        m = 0;
        for (int j = 1; j < m_n; j++)
        {
            if (tmp[m_psa[j - 1]] == tmp[m_psa[j]] 
            && m_psa[j- 1] + i < m_n 
            && m_psa[j] + i < m_n 
            && tmp[m_psa[j - 1] + i] == tmp[m_psa[j] + i])
            {
                m_prank[m_psa[j]] = m;
            }
            else 
            {
                m_prank[m_psa[j]] = ++m;
            }
        }
        
        if (m >= m_n - 1) break;
    }
    
    delete []c;
    delete []tmp;
    
    return;
}

void SuffixArray::buildHeight()
{
    m_pheight[0] = 0;
    
    int k = 0;
    for (int i = 0; i < m_n; i++)
    {
        int curPos = m_prank[i];
        if (curPos == 0) continue;
        curPos--;
        
        int j = m_psa[curPos];
        if (k)
        {
            k--;
        }
        while (i + k < m_n && j + k < m_n && m_a[i + k] == m_a[j + k]) k++;
        m_pheight[m_prank[i]] = k;
    }
}

const int* SuffixArray::getSa()
{
   return m_psa;
}

const int* SuffixArray::getHeight()
{
    return m_pheight;
}

int main(int argc, char **argv)
{
#ifndef ONLINE_JUDGE
    ifstream fin("f:\\OJ\\uva_in.txt");
    streambuf* oldcin = cin.rdbuf(fin.rdbuf());
#endif

    int t;
    string s;
    cin >> t;
    getline(cin, s);
    while (t--)
    {
    	getline(cin, s);
    	int strLen = 0;
    	for (size_t i = 0, len = s.length(); i < len; i++)
    	{
    		str[strLen++] = s[i];
    	}

    	SuffixArray sa(str, strLen);
    	sa.buildSa();
    	sa.buildHeight();

    	const int* psa = sa.getSa();
    	const int* pheight = sa.getHeight();

    	int maxVal = 0;
    	for (int i = 1; i < strLen; i++)
    	{
    		if (pheight[i] > maxVal)
    		{
    			maxVal = pheight[i];
    		}
    	}

    	if (maxVal > 0)
    	{
    		int cnt = 0;
    		int pos = -1;
    		for (int i = 1; i < strLen; i++)
    		{
    			if (pheight[i] == maxVal)
    			{
    				pos = i;
    				while (i < strLen)
    				{
    					if (pheight[i] >= maxVal)
    					{
    						cnt++;
    					}
    					else
    					{
    						break;
    					}
    					i++;
    				}
    				break;
    			}
    		}
    		string ans;
    		for (int i = 0; i < maxVal; i++)
    		{
    			ans += (char)str[psa[pos - 1] + i];
    		}
    		cout << ans << " " << cnt + 1 << endl;
    	}
    	else
    	{
    		cout << "No repetitions found!" << endl;
    	}
    }


#ifndef ONLINE_JUDGE
    cin.rdbuf(oldcin);
#endif
}
