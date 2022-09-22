#include <bits/stdc++.h>

using namespace std;

const int MAXN =  768;

int a[MAXN];
int b[MAXN];

struct Item 
{
    int s;
    int b;
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
            if (i == 0) {
                for (int j = 0; j < n; j++) {
                    cin >> a[j];
                }
                sort(a, a + n);
            } else {
                for (int j = 0; j < n; j++) {
                    cin >> b[j];
                }
                sort(b, b + n);
                
                merge(a, b, a, n);
            }
        }
    
        cout << a[0];
        for (int i = 1; i < n; i++) {
            cout << " " << a[i];
        }
        cout << endl;
    }
    
    
    
    #ifndef ONLINE_JUDGE
        cin.rdbuf(back);
    #endif
    
    return 0;
}