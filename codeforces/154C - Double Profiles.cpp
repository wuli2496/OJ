#include <iostream>
#include <fstream>
#include <algorithm>

using namespace std;

typedef long long ll;

const int mod = 10000007;
const int MAXN = 1000007;

ll ha[MAXN], hb[MAXN];
ll hashValue[MAXN];
int n, m;

void fastio()
{
    ios_base::sync_with_stdio(false);
    cin.tie(NULL);
    cout.tie(NULL);
}

void init()
{
	hashValue[1] = 1;
	for (int i = 2; i <= n; ++i) {
		hashValue[i] = hashValue[i - 1] * mod;
	}
}

void solve()
{
	for (int i = 1; i <= n; ++i) {
		hb[i] = ha[i] + hashValue[i];
	}

	sort(ha + 1, ha + 1 + n);
	sort(hb + 1, hb + 1 + n);

    ll cur = ha[1];
    ll ans = 0;
    ll cnt = 1;

    for (int i = 2; i <= n; ++i) {
    	if (ha[i] == cur) {
    		++cnt;
		} else {
			ans += cnt * (cnt - 1) / 2;
			cnt = 1;
			cur = ha[i];
		}
	}
	ans += cnt * (cnt - 1) / 2;

	cur = hb[1];
	cnt = 1;
	for (int i = 2; i <= n; ++i) {
		if (hb[i] == cur) {
			++cnt;
		} else {
			ans += cnt * (cnt - 1) / 2;
			cnt = 1;
			cur = hb[i];
		}
	}
	ans += cnt * (cnt - 1) / 2;

	cout << ans << endl;
}

int main()
{
    fastio();

    #ifndef ONLINE_JUDGE
        ifstream fin("f:\\OJ\\uva_in.txt");
        streambuf* cinback = cin.rdbuf(fin.rdbuf());
    #endif // ONLINE_JUDGE

    cin >> n >> m;
    init();

    for (int i = 0; i < m; ++i) {
    	int u, v;
    	cin >> u >> v;
    	ha[u] += hashValue[v];
    	ha[v] += hashValue[u];
	}

	solve();


    #ifndef ONLINE_JUDGE
        cin.rdbuf(cinback);
    #endif // ONLINE_JUDGE

    return 0;
}
