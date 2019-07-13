#include <cstdio>
#include <cstdlib>
#include <cctype>
#include <cstring>

using namespace std;

const int MAXN = 255;

double atomic[255];
char buf[MAXN];

void init()
{
	atomic['C'] = 12.01;
	atomic['H'] = 1.008;
	atomic['O'] = 16.00;
	atomic['N'] = 14.01;
}

void solve()
{
	int len = strlen(buf);
	double sum = 0;
	int digits;

	for (int i = 0; i < len; i++) {
		if (isalpha(buf[i])) {
			int j = i + 1;
			digits = 0;
			while (j < len && isdigit(buf[j])) {
				digits = digits * 10 + (buf[j] - '0');
				j++;
			}

			if (digits == 0) digits = 1;

			sum += atomic[buf[i]] * digits;
			i = j - 1;
		}				
	}	

	printf("%.3f\n", sum);
}

int main()
{
	#ifndef ONLINE_JUDGE
		freopen("./uva_in.txt", "r", stdin);
	#endif
	init();

	int n;
	scanf("%d", &n);
	while (n--) {
		scanf("%s", buf);
		solve();
	}
	exit(0);
}
