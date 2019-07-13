//
// Created by John on 2017/7/19.
//

#include <iostream>
#include <queue>
#include <vector>
#include <fstream>

class Solution
{
public:
    void init(int b, int sg, int sb)
    {
        m_b = b; m_sg = sg; m_sb = sb;

        while (!g_queue.empty()) g_queue.pop();
        while (!b_queue.empty()) b_queue.pop();
    }

    void add_g(int num)
    {
        g_queue.push(num);
    }

    void add_b(int num)
    {
        b_queue.push(num);
    }

    void solve()
    {
        std::vector<int> vg(m_b), vb(m_b);
        while (!g_queue.empty() && !b_queue.empty())
        {
            for (int i = 0; i < m_b; i++)
            {
                if (g_queue.empty()) break;
                int num = g_queue.top(); g_queue.pop();
                vg[i] = num;
            }

            for (int i = 0; i < m_b; i++)
            {
                if (b_queue.empty()) break;
                int num = b_queue.top(); b_queue.pop();
                vb[i] = num;
            }

            for (int i = 0; i < m_b; i++)
            {
                if (vg[i] > vb[i])
                {
                    vg[i] -= vb[i];
                    vb[i] = 0;
                }
                else if (vg[i] < vb[i])
                {
                    vb[i] -= vg[i];
                    vg[i] = 0;
                }
                else
                {
                    vg[i] = vb[i] = 0;
                }
            }

            for (int i = 0; i < m_b; i++)
            {
                if (vg[i] != 0)
                {
                    g_queue.push(vg[i]);
                    vg[i] = 0;
                }

                if (vb[i] != 0)
                {
                    b_queue.push(vb[i]);
                    vb[i] = 0;
                }
            }
        }

        if (g_queue.empty() && b_queue.empty())
        {
            std::cout << "green and blue died" << std::endl;
        }
        else if (g_queue.empty())
        {
            std::cout << "blue wins" << std::endl;
            while (!b_queue.empty())
            {
                int num = b_queue.top(); b_queue.pop();
                std::cout << num << std::endl;
            }
        }
        else
        {
            std::cout << "green wins" << std::endl;
            while (!g_queue.empty())
            {
                int num = g_queue.top(); g_queue.pop();
                std::cout << num << std::endl;
            }
        }

    }

private:
    int m_b, m_sg, m_sb;
    std::priority_queue<int> g_queue, b_queue;
};


int main() {
#ifndef ONLINE_JUDGE
    std::ifstream fin("e:\\program\\clion\\uva_in.txt");
    std::streambuf* old = std::cin.rdbuf(fin.rdbuf());
#endif

    int n;
    std::cin >> n;
    Solution solver;
    while (n--)
    {
        int b, sg, sb;
        std::cin >> b >> sg >> sb;
        solver.init(b, sg, sb);
        for (int i = 0; i < sg; i++)
        {
            int num;
            std::cin >> num;
            solver.add_g(num);
        }

        for (int i = 0; i < sb; i++)
        {
            int num;
            std::cin >> num;
            solver.add_b(num);
        }

        solver.solve();
        if (n) std::cout << std::endl;
    }

#ifndef ONLINE_JUDGE
    std::cin.rdbuf(old);
#endif
    return 0;
}