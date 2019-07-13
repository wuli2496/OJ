#include <iostream>
#include <cstring>
#include <fstream>

using namespace std;

const int N = 6;
const int M = 4;
const int LEFT[N] = {1, 5, 2, 3, 0, 4};
const int UP[N] = {3, 1, 0, 5, 4, 2};


void rot(const int *trans, int *v)
{
    int q[N];
    memcpy(q, v, sizeof(q));
    for (int i = 0; i < N; ++i)
    {
        v[i] = trans[q[i]];
    }
}

int main()
{
    #ifndef ONLINE_JUDGE
        ofstream fout("f:\\OJ\\uva_out.txt");
        streambuf *old = cout.rdbuf(fout.rdbuf());
    #endif
    
    cout << "int dice24[24][6] = {" << endl;
    int tmp[N] = {0, 1, 2, 3, 4, 5};
    for (int i = 0; i < N; ++i)
    {
        int init[N];
        memcpy(init, tmp, sizeof(tmp));
        if (i == 0)
        {
            rot(UP, init);
        }
        else if (i == 1)
        {
            rot(LEFT, init); rot(UP, init);
        }
        else if (i == 3)
        {
            rot(UP, init); rot(UP, init);
        }
        else if (i == 4) 
        {
            rot(LEFT, init); rot(LEFT, init); rot(LEFT, init); rot(UP, init);
        }
        else if (i == 5)
        {
            rot(LEFT, init); rot(LEFT, init); rot(UP, init);
        }
            
        
        for (int j = 0; j < M; ++j)
        {
            cout << "{" << init[0] << ", " << init[1] << ", " << init[2] << ", " << init[3] << ", " << init[4] << ", " << init[5] << "}";
            if ((i != N - 1) || (j != M - 1))
            {
                cout << "," << endl;
            }
            rot(LEFT, init);
        }
    }
    cout << endl << "};" << endl;

    #ifndef ONLINE_JUDGE
        cout.rdbuf(old);
    #endif
    return 0;
}