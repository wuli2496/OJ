#include <cstdio>
#include <cstring>
#include <map>

using namespace std;

const int MAXN = 210;

char str[MAXN];

int cal(char ch)
{
    switch (ch) {
        case 'W': return 64;
        case 'H': return 32;
        case 'Q': return 16;
        case 'E': return 8;
        case 'S': return 4;
        case 'T': return 2;
        case 'X': return 1;
    }
}

bool input() 
{
    scanf("%s", str);
    if (strcmp(str, "*") == 0) return false;
    
    return true;
}

void solve() 
{
    char *p = strtok(str, "/");
    int ans = 0;
    
    while (p) {
        int sum = 0;
        for (int i = 0, len = strlen(p); i < len; i++) {
            sum += cal(p[i]);
        }
        if (sum == 64) ans++;
        p = strtok(NULL, "/");
    }
    
    printf("%d\n", ans);
}

int main() 
{
#ifndef ONLINE_JUDGE
    freopen("/cygdrive/d/OJ/uva_in.txt", "r", stdin);
#endif

    while (input()) {
        solve();
    }
    return 0;
}