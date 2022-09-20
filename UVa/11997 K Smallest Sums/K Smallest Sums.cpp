#include <bits/stdc++.h>

using namespace std;

const int MAXN =  768;

int a[MAXN][MAXN];

struct Item 
{
    int s, b;
    Item(int s, int b) : s(s), b(b) {}
    
    bool operator < (const Item& other) const {
        return s > other.s;
    }
};

void merge(int* a, int* b, int* c, int n) 
{
    priority_queue<Item> q;
    for (int i = 0; i < n; i++) {
        q.push(Item(a[i] + b[0], 0));
    }
    
    for (int i = 0; i < n; i++) {
        Item item = q.top(); q.pop();
        c[i] = item.s;
        int btemp = item.b;
        if (btemp + 1 < n) {
            q.push(Item(item.s - b[btemp] + b[btemp + 1], btemp + 1));
        }
    }
}

void fastio() 
{
    ios_base::sync_with_stdio(false);
    cin.tie(nullptr);
    cout.tie(nullptr);
}

int main()
{
    fastio();
    
    #ifndef ONLINE_JUDGE
        ifstream fin("f:\\OJ\\uva_in.txt");
        streambuf* back = cin.rdbuf(fin.rdbuf());
    #endif
    
    int n;
    while (cin >> n) {
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                cin >> a[i][j];
            }
            sort(a[i], a[i] + n);
        }
        
        for (int i = 1; i < n; i++) {
            merge(a[0], a[i], a[0], n);
        }
    
        cout << a[0][0];
        for (int i = 1; i < n; i++) {
            cout << " " << a[0][i];
        }
        cout << endl;
    }
    
    
    
    #ifndef ONLINE_JUDGE
        cin.rdbuf(back);
    #endif
    
    return 0;
}