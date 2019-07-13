//
// Created by John on 2017/8/26.
//

#include <iostream>
#include <fstream>
#include <cstring>

using namespace std;

#define lson(x) ((x) << 1)
#define rson(x) ((x << 1) + 1)

const int N = 4000001;
const int STR_LEN = 60;


class Node
{
public:
    int l, r, v, setv, resv;

    void set(int l, int r, int v, int setv, int resv)
    {
        this->l = l;
        this->r = r;
        this->v = v;
        this->setv = setv;
        this->resv = resv;
    }
};
Node node[N];
int v[N];

class RMQ
{
public:
    RMQ()
    {
        tree = node;
    }
    void build(int root, int left, int right)
    {
        if (left == right)
        {
            tree[root].set(left, right, v[left - 1], -1, 0);
        }
        else
        {
            int mid = (left + right) >> 1;
            build(lson(root), left, mid);
            build(rson(root), mid + 1, right);
            pushup(root);
        }
    }

    void update(int root, int left, int right, int v)
    {
        if (left <= node[root].l && right >= tree[root].r)
        {
            set_node(root, v);
            return;
        }

        int mid = (tree[root].l + tree[root].r) >> 1;
        pushdown(root);
        if (left <= mid) update(lson(root), left, right, v);
        if (right > mid) update(rson(root), left, right, v);
        pushup(root);
    }

    void reverse(int root, int left, int right)
    {
        if (left <= tree[root].l && right >= tree[root].r)
        {
            res_node(root);
            return;
        }

        int mid = (tree[root].l + tree[root].r) >> 1;
        pushdown(root);
        if (left <= mid) reverse(lson(root), left, right);
        if (right > mid) reverse(rson(root), left, right);
        pushup(root);
    }

    int query(int root, int left, int right)
    {
        if (left <= tree[root].l && right >= tree[root].r)
        {
            return tree[root].v;
        }

        int mid = (tree[root].l + tree[root].r) >> 1;
        pushdown(root);
        int result = 0;
        if (left <= mid) result += query(lson(root), left, right);
        if (right > mid) result += query(rson(root), left, right);
        pushup(root);

        return result;
    }

private:
    void pushup(int root)
    {
        tree[root].set(tree[lson(root)].l, tree[rson(root)].r, tree[lson(root)].v + tree[rson(root)].v, -1, 0);
    }

    void set_node(int u, int v)
    {
        tree[u].setv = v;
        tree[u].resv = 0;
        tree[u].v = v * (tree[u].r - tree[u].l + 1);
    }

    void res_node(int u)
    {
        tree[u].resv ^= 1;
        tree[u].v = tree[u].r - tree[u].l + 1 - tree[u].v;
    }

    void pushdown(int root)
    {
        if (tree[root].setv >= 0)
        {
            set_node(lson(root), tree[root].setv);
            set_node(rson(root), tree[root].setv);
            tree[root].setv = -1;
        }

        if (tree[root].resv)
        {
            res_node(lson(root));
            res_node(rson(root));
            tree[root].resv = 0;
        }
    }

private:
    Node *tree;
};


int main()
{
#ifndef ONLINE_JUDGE
    std::ifstream fin("f:\\OJ\\uva_in.txt");
    std::streambuf* back = std::cin.rdbuf(fin.rdbuf());
#endif

    int t;

    RMQ rmq;

    std::cin >> t;
    for (int i = 1; i <= t; i++) {
        int m;
        std::cin >> m;
        char str[STR_LEN];

        memset(v, 0x00, sizeof(v));
        int len = 0;
        while (m--)
        {
            int t;
            cin >> t >> str;
            int n = strlen(str);
            for (int j = 0; j < t; j++)
            {
                for (int k = 0; k < n; k++)
                {
                    if (str[k] == '1')
                    {
                        v[len] = 1;
                    }
                    len++;
                }
            }
        }

        rmq.build(1, 1, len);
        int q;
        std::cin >> q;
        int qcnt = 1;
        std::cout << "Case " << i << ":" << std::endl;
        for (int i = 0; i < q; i++)
        {
            int a, b;
            std::cin >> str >> a >> b;

            if (str[0] == 'F') rmq.update(1, a + 1, b + 1, 1);
            else if (str[0] == 'E') rmq.update(1, a + 1, b + 1, 0);
            else if (str[0] == 'I') rmq.reverse(1, a + 1, b + 1);
            else if (str[0] == 'S')
            {
                int ans = rmq.query(1, a + 1, b + 1);
                std::cout << "Q" << qcnt++ << ": " << ans << std::endl;
            }
        }
    }


#ifndef ONLINE_JUDGE
    std::cin.rdbuf(back);
#endif

    return 0;
}
