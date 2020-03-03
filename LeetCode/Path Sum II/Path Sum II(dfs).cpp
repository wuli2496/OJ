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
    vector<vector<int>> pathSum(TreeNode* root, int sum)
    {
        if (root == nullptr)
        {
            return vector<vector<int>>();
        }

        vector<vector<int>> ans;
        vector<int> row;

        dfs(root, 0, sum, ans, row);

        return ans;
    }

private:
    void dfs(TreeNode* root, int curSum, int sum, vector<vector<int>>& ans, vector<int>& row)
    {
        if (root == nullptr)
        {
            return;
        }

        if (root != nullptr && root->left == nullptr && root->right == nullptr)
        {
            if (curSum + root->val == sum)
            {
                row.push_back(root->val);
                ans.push_back(row);
                row.pop_back();
                return;
            }
            return;
        }

        row.push_back(root->val);
        dfs(root->left, curSum + root->val, sum, ans, row);
        row.pop_back();

        row.push_back(root->val);
        dfs(root->right, curSum + root->val, sum, ans, row);
        row.pop_back();

    }
};