#include <iostream>
#include <string>
#include <fstream>
#include <cstring>
#include <vector>
#include <algorithm>

using namespace std;

const int N = 256;
const int MIN = 0x3f3f3f3f;
const int MAXN = 100010;

class SuffixArray
{
public:
    SuffixArray(int* pa, int n);
    ~SuffixArray();
    
    void buildSa();
    void getHeight();
    
    const int* getSa();
    const int* getH();
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
    int n = max(N, m_n);
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

void SuffixArray::getHeight()
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

const int* SuffixArray::getH()
{
    return m_pheight;
}

class Result
{
public:
	int len, pos;

	bool operator < (const Result& other) const
	{
		if (len != other.len)
		{
			return len > other.len;
		}

		return pos < other.pos;
	}
};

int a[MAXN];

int main(int argc, char **argv)
{
#ifndef ONLINE_JUDGE
    ifstream fin("f:\\OJ\\uva_in.txt");
    streambuf* old = cin.rdbuf(fin.rdbuf());
#endif

    int k;
    int testCase = 0;

    while (cin >> k)
    {
    	if (0 == k)
    	{
    		break;
    	}

    	string tdpCode, jcnCode, tmp;
    	getline(cin, tmp);
    	getline(cin, tmp);
    	while (getline(cin, tmp))
    	{
    		if ("END TDP CODEBASE" == tmp)
    		{
    			break;
    		}
    		tdpCode += tmp;
    		tdpCode += "\n";
    	}

    	getline(cin, tmp);
    	while (getline(cin, tmp))
    	{
    		if ("END JCN CODEBASE" == tmp)
    		{
    			break;
    		}
    		jcnCode += tmp;
    		jcnCode += "\n";
    	}


    	string str = jcnCode + tdpCode;
    	int tdpCodeLen = tdpCode.length();
    	
    	for (int i = 0; i < tdpCodeLen; i++)
    	{
    		a[i] = tdpCode[i];
    	}
    	a[tdpCodeLen] = N - 1;
    	int jcnCodeLen = jcnCode.length();
    	for (int i = 0; i < jcnCodeLen + 1; i++)
    	{
    		a[tdpCodeLen + i + 1] = jcnCode[i];
    	}
    	SuffixArray suffixArray(a, tdpCodeLen + jcnCodeLen + 1);
    	suffixArray.buildSa();
    	suffixArray.getHeight();

    	const int* psa = suffixArray.getSa();
    	const int* pheight = suffixArray.getH();
    	int saLen = tdpCodeLen + jcnCodeLen + 1;

    	int* pCnt = new int[jcnCodeLen];
    	memset(pCnt, 0, sizeof(int) * jcnCodeLen);
    	int minVal = -1;
    	for (int i = 0; i < saLen; i++)
    	{
    		if (psa[i] < tdpCodeLen)
			{
    			minVal = MIN;
			}
    		else if (psa[i] > tdpCodeLen)
    		{
    			if (-1 == minVal)
    			{
    				continue;
    			}

    			minVal = min(pheight[i], minVal);
    			pCnt[psa[i] - 1 - tdpCodeLen] = max(pCnt[psa[i] - 1 - tdpCodeLen], minVal);
    		}
    	}

    	minVal = -1;
    	for (int i = saLen - 1; i >= 0; i--)
    	{
    		if (psa[i] < tdpCodeLen)
    		{
    			minVal = MIN;
    		}
    		else if (psa[i] > tdpCodeLen)
    		{
    			if (-1 == minVal)
    			{
    				continue;
    			}

    			minVal = min(pheight[i + 1], minVal);
    			pCnt[psa[i] - 1 - tdpCodeLen] = max(pCnt[psa[i] - 1 - tdpCodeLen], minVal);
    		}
    	}

    	vector<Result> result;
    	int curPos = -1;
    	for (int i = 0; i < jcnCodeLen; i++)
    	{
    		if ((i + pCnt[i] <= curPos) || pCnt[i] <= 0) continue;
    		result.push_back((Result){pCnt[i], i});
    		curPos = i + pCnt[i];
    	}
    	delete []pCnt;

    	sort(result.begin(), result.end());
    	if (testCase)
		{
			cout << endl;
		}
    	cout << "CASE " << ++testCase << endl;
    	for (int i = 0; i < min(k, (int)result.size()); i++)
    	{
    		cout << "INFRINGING SEGMENT " << i + 1 << " LENGTH " << result[i].len << " POSITION " << result[i].pos << endl;
    		cout << jcnCode.substr(result[i].pos, result[i].len) << endl;
    	}
    }
    
#ifndef ONLINE_JUDGE
    cin.rdbuf(old);
#endif
}
