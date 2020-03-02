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
    vector<vector<int>> zigzagLevelOrder(TreeNode* root)
    {
        if (root == nullptr)
        {
            return vector<vector<int>>();
        }

        vector<TreeNode*> lay;
        lay.push_back(root);
        vector<vector<int>> ans;
        dfs(lay, 0, ans);

        return ans;
    }

private:
    void dfs(vector<TreeNode*> lay, int depth, vector<vector<int>>& ans)
    {
        if (lay.empty())
        {
            return;
        }

        int size = lay.size();
        vector<int> values(size);
        vector<TreeNode*> newLayer;
        for (int i = 0; i < size; ++i)
        {
            if (depth % 2 == 0)
            {
                values[i] = lay[i]->val;
            }
            else
            {
                values[size - 1 - i] = lay[i]->val;
            }

            if (lay[i]->left != nullptr)
            {
                newLayer.push_back(lay[i]->left);
            }
            if (lay[i]->right != nullptr)
            {
                newLayer.push_back(lay[i]->right);
            }
        }

        ans.push_back(values);
        dfs(newLayer, depth + 1, ans);
    }
};