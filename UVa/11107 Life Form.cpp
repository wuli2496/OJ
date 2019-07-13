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
const int N = 260;
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

bool check(vector<set<int> >& v, const int* pa, int pan, vector<string>& vecStr);
int checkPos(int sa, vector<string>& vecStr);
vector<set<int> > getSeg(const int* psa, const int *pheight, int n, int mid);
vector<string> getAns(vector<set<int> >& v, const int* pa, int pan, vector<string>& vecStr, int len, int* pStr, int strLen);

int main(int argc, char **argv)
{
#ifndef ONLINE_JUDGE
    ifstream fin("f:\\OJ\\uva_in.txt");
    ofstream fout("f:\\OJ\\uva_out.txt");
    streambuf* oldcin = cin.rdbuf(fin.rdbuf());
    streambuf* oldcout = cout.rdbuf(fout.rdbuf());
#endif

    int n;
    string s;
    int testCase = 0;

    while (cin >> n)
    {
    	if (0 == n)
    	{
    		break;
    	}
    	getline(cin, s);
    	vector<string> vecStr;
    	int strLen = 0;

    	for (int i = 0; i < n; i++)
    	{
    		getline(cin, s);
    		vecStr.push_back(s);

    		int sLen = s.length();
    		for (int j = 0; j < sLen; j++)
    		{
    			str[strLen++] = s[j];
    		}
    		str[strLen++] = N + i;
    	}

    	SuffixArray sa(str, strLen);
    	sa.buildSa();
    	sa.buildHeight();

    	const int* psa = sa.getSa();
    	const int* pheight = sa.getHeight();

    	int maxLen = INT_MAX;
    	for (int i = 0, vecStrSize = vecStr.size(); i < vecStrSize; i++)
    	{
    		maxLen = min(maxLen, static_cast<int>(vecStr[i].size()));
    	}

    	int l = 0, r = maxLen + 1;
    	while (l < r)
    	{
    		int mid = (l + r) >> 1;

    		vector<set<int> > seg = getSeg(psa, pheight, strLen, mid);
    		bool ok = check(seg, psa, strLen, vecStr);
    		if (ok)
    		{
    			l = mid + 1;
    		}
    		else
    		{
    			r = mid;
    		}
    	}

    	if (testCase++)
    	{
    		cout << endl;
    	}
        
        if (1 == n)
        {
            cout << vecStr[0] << endl;
            continue;
        }
        
    	if (0 == l)
    	{
    		cout << "?" << endl;
    	}
    	else
    	{
    		vector<set<int> > seg = getSeg(psa, pheight, strLen, l - 1);
    		vector<string> ans = getAns(seg, psa, strLen, vecStr, l - 1, str, strLen);
    		sort(ans.begin(), ans.end());
    		for (size_t i = 0, ansSize = ans.size(); i < ansSize; i++)
            {
                cout << ans[i] << endl;
            }
    	}
    }


#ifndef ONLINE_JUDGE
    cin.rdbuf(oldcin);
    cout.rdbuf(oldcout);
#endif
}

bool check(vector<set<int> >& v, const int* pa, int pan, vector<string>& vecStr)
{
	for (size_t i = 0; i < v.size(); i++)
	{
		set<int> &intSet = v[i];
		bool *flag = new bool[vecStr.size()];
		memset(flag, false, sizeof(bool) * (int)vecStr.size());
		for (set<int>::iterator it = intSet.begin(); it != intSet.end(); it++)
		{
			int sa = pa[*it];
			int retVal = checkPos(sa, vecStr);
			if (0 <= retVal)
			{
				flag[retVal] = true;
			}
		}

		int cnt = 0;
		for (size_t i = 0, vecStrSize = vecStr.size(); i < vecStrSize; i++)
		{
			if (flag[i])
			{
				cnt++;
			}
		}

		delete []flag;

		if (cnt > (int)vecStr.size() / 2)
		{
			return true;
		}
	}

	return false;
}

int checkPos(int sa, vector<string>& vecStr)
{
	for (size_t i = 0, vecStrSize = vecStr.size(); i < vecStrSize; i++)
	{
		if (sa > (int)vecStr[i].size())
		{
			sa -= vecStr[i].size() + 1;
		}
		else if (sa == (int)vecStr[i].size())
		{
			return -1;
		}
		else
		{
			return i;
		}
	}

	return -1;
}

vector<set<int> > getSeg(const int* psa, const int *pheight, int n, int mid)
{
	vector<set<int> > seg;
	set<int> intSet;
	intSet.insert(0);
	for (int i = 1; i < n; i++)
	{
		if (mid > 0 && pheight[i] >= mid && memcmp((const void*)&str[psa[i - 1]], (const void*)&str[psa[i]], sizeof(int) * pheight[i]) == 0)
		{
			intSet.insert(i);
		}
		else
		{
			seg.push_back(intSet);
			intSet.clear();
			intSet.insert(i);
		}
	}
	if (!intSet.empty())
	{
		seg.push_back(intSet);
	}

	return seg;
}

vector<string> getAns(vector<set<int> >& v, const int* pa, int pan, vector<string>& vecStr, int len, int* pStr, int strLen)
{
	vector<string> ans;

	for (size_t i = 0; i < v.size(); i++)
		{
			set<int> &intSet = v[i];
			bool *flag = new bool[vecStr.size()];
			memset(flag, false, sizeof(bool) * (int)vecStr.size());
			for (set<int>::iterator it = intSet.begin(); it != intSet.end(); it++)
			{
				int sa = pa[*it];
				int retVal = checkPos(sa, vecStr);
				if (0 <= retVal)
				{
					flag[retVal] = true;
				}
			}

			int cnt = 0;
			for (size_t i = 0, vecStrSize = vecStr.size(); i < vecStrSize; i++)
			{
				if (flag[i])
				{
					cnt++;
				}
			}

			delete []flag;

			if (cnt > (int)vecStr.size() / 2)
			{
				int sa = pa[*intSet.begin()];
				string s;
				for (int i = sa; i < sa + len; i++)
				{
					s += (char)pStr[i];
				}
				ans.push_back(s);
			}
		}

	return ans;
}
