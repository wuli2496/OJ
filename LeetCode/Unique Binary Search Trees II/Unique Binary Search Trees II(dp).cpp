struct TreeNode
{
    int val;
    TreeNode *left;
    TreeNode *right;
    TreeNode(int x) : val(x), left(nullptr), right(nullptr) {}
};

class Solution
{
public:
    vector<TreeNode*> generateTrees(int n)
    {
        if (n == 0)
        {
            return vector<TreeNode*>();
        }

        vector<TreeNode*> ans[n + 1];
        ans[0].push_back(nullptr);

        for (int i = 1; i <= n; ++i)
        {
            for (int j = 0; j < i; ++j)
            {
                vector<TreeNode*> left = ans[j];
                vector<TreeNode*> right = ans[i - 1 - j];
                for (auto l : left)
                {
                    for (auto w : right)
                    {
                        TreeNode* node = new TreeNode(j + 1);
                        node->left = l;
                        node->right = clone(w, j + 1);
                        ans[i].push_back(node);
                    }
                }
            }
        }

        return ans[n];
    }

private:
    TreeNode* clone(TreeNode* node, int offset)
    {
        if (node == nullptr)
        {
            return nullptr;
        }

        TreeNode* root = new TreeNode(node->val + offset);
        root->left = clone(node->left, offset);
        root->right = clone(node->right, offset);

        return root;
    }
};