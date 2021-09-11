class Solution 
{
public:
    int findContentChildren(vector<int>& g, vector<int>& s) {
        sort(g.begin(), g.end());
        sort(s.begin(), s.end());
        
        int ans = 0;
        vector<int>::iterator start = s.begin(), end = s.end();
        for (int num : g) {
            vector<int>::iterator it = lower_bound(start, end, num);
            if (it == end) {
                break;
            }
            
            ++ans;
            start = ++it;
        }
        
        return ans;
    }
};