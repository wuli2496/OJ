/*
ID: wuli2491
TASK: sort3
LANG: C++
*/
#include <iostream>
#include <fstream>
#include <memory>
#include <vector>
#include <algorithm>
#include <cstring>

using namespace std;

template<typename Result>
class AlgoPolicy
{
public:
	virtual ~AlgoPolicy() {}
	virtual Result execute() = 0;
};

template<typename Result>
class InputPolicy
{
public:
	virtual ~InputPolicy() {}

	virtual bool hasNext() = 0;

	virtual Result next() = 0;
};

class OutputPolicy
{
public:
	virtual ~OutputPolicy() {}

	virtual void write() = 0;
};

struct Data
{
	vector<int> nums;
};

class BaseAlgo : public AlgoPolicy<int>
{
public:
	BaseAlgo(vector<int>& otherNums) 
		: nums(otherNums)
	{
	}

	int execute() override
	{
		vector<int> target = nums;
		sort(target.begin(), target.end());
		const int N = 4;
		int state[N][N];
		memset(state, 0x00, sizeof(state));
		for (size_t i = 0; i < target.size(); ++i)
		{
			++state[target[i]][nums[i]];
		}
		

		int exchange1_2 = min(state[1][2], state[2][1]);
		int exchange1_3 = min(state[1][3], state[3][1]);
		int exchange2_3 = min(state[2][3], state[3][2]);

		int count = state[1][2] + state[1][3] - exchange1_2 - exchange1_3;
		count *= 2;
		count += exchange1_2 + exchange1_3 + exchange2_3;

		return count;
	}

private:
	vector<int>& nums;
};

class LimitInput : public InputPolicy<Data>
{
public:
	LimitInput(istream& in) : instream(in)
	{
	
	}

	bool hasNext() override
	{
		instream >> n;
		return !instream.eof();
	}

	Data next() override
	{
		
		for (int i = 0; i < n; ++i)
		{
			int num;
			instream >> num;
			data.nums.push_back(num);
		}

		return data;
	}
private:
	istream& instream;
	Data data;
	int n;
};

class Output : public OutputPolicy
{
public:
	Output(ostream& out, int& v) : outstream(out), ans(v)
	{

	}

	void write() override
	{
		outstream << ans << endl;
	}
private:
	int& ans;
	ostream& outstream;
};
template <typename Result>
class Solution
{
public:
	Solution(AlgoPolicy<Result>* algo)
	{
		pimpl.reset(algo);
	}

	Result run()
	{
		return pimpl->execute();
	}

private:
	shared_ptr<AlgoPolicy<Result>> pimpl;
};
int main(int argc, char** argv)
{
	ios::sync_with_stdio(false);
	cin.tie(nullptr);

#ifndef ONLINE_JUDGE
	ifstream fin("sort3.in");
	streambuf* cinback = cin.rdbuf(fin.rdbuf());

	ofstream fout("sort3.out");
	streambuf* coutback = cout.rdbuf(fout.rdbuf());
#endif

	shared_ptr<InputPolicy<Data>> in(new LimitInput(cin));
	while (in.get() != nullptr && in->hasNext())
	{
		Data data = in->next();
		AlgoPolicy<int>* algo = new BaseAlgo(data.nums);
		Solution<int> solution(algo);
		int ans = solution.run();
		shared_ptr<OutputPolicy> output(new Output(cout, ans));
		output->write();
	}

#ifndef ONLINE_JUDGE
	cin.rdbuf(cinback);
	cout.rdbuf(coutback);
#endif

	return 0;
}
