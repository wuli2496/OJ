#include <iostream>
#include <fstream>
#include <sstream>
#include <vector>
#include <algorithm>
#include <memory>

using namespace std;

template<typename Result>
class AlgoPolicy
{
public:
    virtual ~AlgoPolicy() {}
    virtual Result execute() = 0;
};


class AdcAlgo : public AlgoPolicy<vector<string>>
{
public:
    explicit AdcAlgo(string s)
    {
        str = s;
    }

    virtual vector<string> execute() override
    {
        stringstream s(str);
        string temp;
        getline(s, temp, ':');
        int hour = atoi(temp.c_str());
        getline(s, temp, ':');
        int minute = atoi(temp.c_str());
        getline(s, temp, ':');
        int second = atoi(temp.c_str());

        string strHour = convertToBin(hour);
        string strMinute = convertToBin(minute);
        string strSecond = convertToBin(second);

        string vertical, horizontal;
        for (size_t i = 0; i < MAXN; ++i)
        {
            vertical += strHour[i];
            vertical += strMinute[i];
            vertical += strSecond[i];
        }

        horizontal = strHour + strMinute + strSecond;

        vector<string> ans{vertical, horizontal};
        return ans;
    }

private:
    string convertToBin(int num)
    {
        string temp;
        while (num > 0)
        {
            int remainder = num % 2;

            temp += char('0' + remainder);

            num /= 2;
        }

        reverse(temp.begin(), temp.end());

        string ans;
        if (temp.length() < MAXN)
        {
            ans += string(MAXN - temp.length(), '0');
        }
        ans += temp;

        return ans;
    }

private:
    enum {MAXN = 6};
private:
    string str;
};

AlgoPolicy<vector<string>>* makeAlgo(const string& name, string s)
{
    if (name == "adc")
    {
        return new AdcAlgo(s);
    }

    return nullptr;
}

class Solution
{
public:
    Solution(AlgoPolicy<vector<string>>* algo)
    {
        pimpl.reset(algo);
    }

    vector<string> run()
    {
        return pimpl->execute();
    }

private:
    shared_ptr<AlgoPolicy<vector<string>>> pimpl;
};

int main(int argc, char **argv)
{
	ios::sync_with_stdio(false);
    cin.tie(nullptr);

#ifndef ONLINE_JUDGE
    ifstream fin("F:\\OJ\\uva_in.txt");
    streambuf* cinback = cin.rdbuf(fin.rdbuf());
#endif

    size_t n;
    cin >> n;
    string s;
    getline(cin, s);
    for (size_t i = 0; i < n; ++i)
    {
        getline(cin, s);
        AlgoPolicy<vector<string>>* algo = makeAlgo("adc", s);
        Solution solution(algo);
        vector<string> ans = solution.run();

        cout << i + 1;

        for (size_t j = 0; j < ans.size(); ++j)
        {
            cout << " " << ans[j];
        }
        cout << endl;
    }
    
#ifndef ONLINE_JUDGE
    cin.rdbuf(cinback);
#endif

	return 0;
}
