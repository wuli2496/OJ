#include <cstdio>
#include <cstring>

using namespace std;


int main()
{
    int ch;
    int num = 0;

    //freopen("f:\\OJ\\uva_in.txt", "r", stdin);

    while ((ch = getchar()) != -1) {
        if (ch == '"') {
            if (num % 2 == 0) {
                putchar('`');
                putchar('`');
            } else {
                putchar('\'');
                putchar('\'');
            }
            ++num;
        } else {
            putchar(ch);
        }
    }

    return 0;
}
