struct TreeNode
{
  int val;
  TreeNode *left;
  TreeNode *right;
  TreeNode(int x) : val(x), left(nullptr), right(nullptr)
  {}
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

        return generate(1, n);
    }

private:
    vector<TreeNode*> generate(int l, int r)
    {
        vector<TreeNode*> result;
        if (l > r)
        {
            result.push_back(nullptr);
            return result;
        }

        for (int i = l; i <= r; ++i)
        {
            vector<TreeNode*> left = generate(l, i - 1);
            vector<TreeNode*> right = generate(i + 1, r);

            for (size_t j = 0; j < left.size(); ++j)
            {
                for (size_t k = 0; k < right.size(); ++k)
                {
                    TreeNode* node = new TreeNode(i);
                    node->left = left[j];
                    node->right = right[k];

                    result.push_back(node);
                }
            }
        }

        return result;
    }
};