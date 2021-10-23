#include <bits/stdc++.h>

using namespace std;

typedef long long ull;

const int R = 131;
const ull MOD = 1e9 + 7;


#define REP(i, a, b) for (int i = (a); i < (b); ++i)

class RabinKarp
{
public:
	RabinKarp(const string& pattern) : pat(pattern)
	{
		patLen = pat.size();
		
		rm = 1;
		
		REP(i, 0, patLen - 1) {
			rm = rm * R %  MOD;
		}
		
		patHash = calHash(pat, patLen);
	}
	
	bool search(const string& text)
	{
		int textLen = text.size();
		ull textHash = calHash(text, patLen);
		
		if (textHash == patHash && check(text, 0)) {
			return true;
		} 
		
		REP(i, patLen, textLen) {
			textHash = (textHash - rm * text[i - patLen] % MOD + MOD) % MOD;
			textHash = (textHash * R + text[i]) % MOD;
			if (textHash == patHash && check(text, i - patLen + 1)) {
				return true;
			}
 		}
 		
 		return false;
	}
	
private:
	ull calHash(const string& s, int len)
	{
		ull h = 0;
		REP(i, 0, len) {
			h = (h * R + s[i]) % MOD;
		}
		
		return h;
	}
	
	bool check(const string& txt, int i)
	{	
		/*
		REP(j, 0, patLen) {
			if (pat[j] != txt[i + j]) {
				return false;
			}
		}
		*/
		return true;
	}

private:
	const string& pat;
	int patLen;
	ull patHash;
	ull rm;
		
};

void fastio()
{
    ios_base::sync_with_stdio(false);
    cin.tie(NULL);
    cout.tie(NULL);
}


int main()
{
    fastio();

    #ifndef ONLINE_JUDGE
        ifstream fin("f:\\OJ\\uva_in.txt");
        streambuf* cinback = cin.rdbuf(fin.rdbuf());
    #endif // ONLINE_JUDGE
	string m;
    cin >> m;

    int n;
    cin >> n;
    while (n--) {
        string s;
        cin >> s;
        RabinKarp rabinKarp(s);
        bool ans = rabinKarp.search(m);
        cout << (ans ? "Y" : "N") << endl;
    }
    #ifndef ONLINE_JUDGE
        cin.rdbuf(cinback);
    #endif // ONLINE_JUDGE

    return 0;
}
