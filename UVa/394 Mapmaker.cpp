#include <cstdio>
#include <string>
#include <iostream>
#include <vector>
#include <utility>
#include <map>
#include <algorithm>
#include <fstream>

using namespace std;


void fastio()
{
    ios_base::sync_with_stdio(false);
    cin.tie(NULL);
    cout.tie(NULL);
}

struct ArrayInfo
{
    string name;
    int baseaddr;
    int elemsize;
    int dime;
    vector<pair<int, int>> dimRange;
    vector<int> c;
    int c0;
};

const int M = 100;
ArrayInfo arrays[M];

void calc(ArrayInfo& arrayInfo)
{
    arrayInfo.c.push_back(arrayInfo.elemsize);
    int dim = arrayInfo.dime;
    for (int i = dim - 2; i >= 0; --i) {
        int prev = arrayInfo.c.back();
        int c = prev * (arrayInfo.dimRange[i + 1].second - arrayInfo.dimRange[i + 1].first + 1);
        arrayInfo.c.push_back(c);
    }

    reverse(arrayInfo.c.begin(), arrayInfo.c.end());

    int sum = 0;
    for (int i = 0; i < arrayInfo.dime; ++i) {
        sum += arrayInfo.c[i] * arrayInfo.dimRange[i].first;
    }

    arrayInfo.c0 = arrayInfo.baseaddr - sum;
}

int main()
{

    fastio();
    #ifndef ONLINE_JUDGE
        ifstream fin("f:\\OJ\\uva_in.txt");
        streambuf* cinback = cin.rdbuf(fin.rdbuf());
    #endif // ONLINE_JUDGE

    map<string, ArrayInfo> nameArrayInfoMap;

    int n, r;
    cin >> n >> r;
    for (int i = 0; i < n; ++i) {
        ArrayInfo arrayInfo;
        cin >> arrayInfo.name >> arrayInfo.baseaddr >> arrayInfo.elemsize >> arrayInfo.dime;
        for (int j = 0; j < arrayInfo.dime; ++j) {
            int low, high;
            cin >> low >> high;
            pair<int, int> pii = {low, high};
            arrayInfo.dimRange.push_back(pii);
        }

        arrays[i] = arrayInfo;
        calc(arrays[i]);
        nameArrayInfoMap[arrayInfo.name] = arrays[i];
    }

    for (int i = 0; i < r; ++i) {
        string name;
        cin >> name;
        const ArrayInfo& arrayInfo = nameArrayInfoMap[name];
        vector<int> indexs;
        for (int j = 0; j < arrayInfo.dime; ++j) {
            int index;
            cin >> index;
            indexs.push_back(index);
        }

        int sum = 0;
        for (int j = 0; j < arrayInfo.dime; ++j){
            sum += arrayInfo.c[j] * indexs[j];
        }
        sum += arrayInfo.c0;
        cout << name << "[";
        for (int j = 0; j < arrayInfo.dime; ++j) {
            if (j != 0) {
                cout << ", ";
            }
            cout << indexs[j];
        }
        cout << "] = ";
        cout << sum << endl;
    }

    #ifndef ONLINE_JUDGE
        cin.rdbuf(cinback);
    #endif // ONLINE_JUDGE
    return 0;
}
