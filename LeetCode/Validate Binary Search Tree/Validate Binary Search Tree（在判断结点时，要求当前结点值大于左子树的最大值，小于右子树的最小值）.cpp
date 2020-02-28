/*
*在判断结点时，要求当前结点值大于左子树的最大值，小于右子树的最小值
*/
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
    bool isValidBST(TreeNode* root)
    {
        if (root == nullptr)
        {
            return true;
        }

        if (root->left != nullptr)
        {
            if (maxValue(root->left) > root->val)
            {
                return false;
            }
        }

        if (root->right != nullptr)
        {
            if (minValue(root->right) < root->val)
            {
                return false;
            }
        }

        if (!isValidBST(root->left) || !isValidBST(root->right))
        {
            return false;
        }

        return true;
    }

private:
    int maxValue(TreeNode* node)
    {
        if (node == nullptr)
        {
            return INT_MAX;
        }

        while (node->right != nullptr)
        {
            node = node->right;
        }

        return node->val;
    }

    int minValue(TreeNode* node)
    {
        if (node == nullptr)
        {
            return INT_MIN;
        }

        while (node->left != nullptr)
        {
            node = node->left;
        }

        return node->val;
    }
};
