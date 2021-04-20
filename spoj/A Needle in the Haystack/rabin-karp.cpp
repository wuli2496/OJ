#include <iostream>
#include <vector>
#include <string>
#include <algorithm>

using namespace std;

const int P = 31;
const int M = 1e9 + 9;

vector<int> rabin_karp(const string& needle, const string& haystack)
{
    int needleLen = needle.length();
    int haystackLen = haystack.length();
    
    vector<long long> pPow(max(needleLen, haystackLen));
    pPow[0] = 1;
    for (int i = 1; i < (int)pPow.size(); ++i) {
        pPow[i] = pPow[i - 1] * P % M;
    }
    
    vector<long long> h(haystackLen, 0);
    h[0] =  haystack[0] - 'a';
    for (int i = 1; i < haystackLen; ++i) {
        h[i] = (h[i - 1] + (haystack[i] - 'a') * pPow[i]) % M;
    }
    
    long long needleHash = 0;
    for (int i = 0; i < needleLen; ++i) {
        needleHash = (needleHash + (needle[i] - 'a') * pPow[i]) % M;
    }
    
    vector<int> result;
    for (int i = 0; i + needleLen <= haystackLen; ++i) {
        long long curHash;
        if (i == 0) {
            curHash = h[needleLen - 1];
        } else {
            curHash = (h[i + needleLen - 1] - h[i - 1] + M) % M;
        }
        
        if (curHash == needleHash * pPow[i] % M) {
            result.emplace_back(i);
        }
    }
    
    return result;
}

int main()
{
    ios_base::sync_with_stdio(false);
    cin.tie(NULL);
    cout.tie(NULL);
    int n;
    string needle, haystack;
    while (cin >> n) {
        cin >> needle >> haystack;
        vector<int> result = rabin_karp(needle, haystack);
        for (auto& num : result) {
            cout << num << endl;
        }
    }
   
    return 0;
}

