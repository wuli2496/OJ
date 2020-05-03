/*
ID: wuli2491
TASK: frac1
LANG: C++
*/
#include <iostream>
#include <fstream>
#include <memory>
#include <string>
#include <vector>
#include <algorithm>

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
	int n;
};

struct Fraction
{
	int numerator;
	int denominator;
};

bool cmp(Fraction a, Fraction b)
{
	return a.numerator * b.denominator < a.denominator * b.numerator;
}

int gcd(int n, int m)
{
	return m == 0 ? n : gcd(m, n % m);
}

class BruteForceAlgo : public AlgoPolicy<vector<Fraction>>
{
public:
	BruteForceAlgo(int n)
	{
		this->n = n;
	}

	vector<Fraction> execute() override
	{
		vector<Fraction> ans;
		Fraction fraction;
		fraction.numerator = 0; fraction.denominator = 1;
		ans.push_back(fraction);

		for (int i = 1; i <= n; ++i)
		{
			for (int j = i + 1; j <= n; ++j)
			{
				if (gcd(i, j) == 1)
				{
					fraction.numerator = i; fraction.denominator = j;
					ans.push_back(fraction);
				}
			}
		}

		fraction.denominator = 1; fraction.numerator = 1;
		ans.push_back(fraction);

		sort(ans.begin(), ans.end(), cmp);

		return ans;
	}

private:
	int n;
};

class LimitInput : public InputPolicy<Data>
{
public:
	LimitInput(istream& in) : instream(in)
	{
		first = true;
	}

	bool hasNext() override
	{
		return first;
	}

	Data next() override
	{
		cin >> data.n;

		first = false;
		return data;
	}
private:
	istream& instream;
	Data data;
	bool first;
};

class Output : public OutputPolicy
{
public:
	Output(ostream& out, vector<Fraction>& v) : outstream(out), ans(v)
	{

	}

	void write() override
	{
		for (Fraction& fraction : ans)
		{
			outstream << fraction.numerator << "/" << fraction.denominator << endl;
		}
	}
private:
	vector<Fraction>& ans;
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
	ifstream fin("frac1.in");
	streambuf* cinback = cin.rdbuf(fin.rdbuf());

	ofstream fout("frac1.out");
	streambuf* coutback = cout.rdbuf(fout.rdbuf());
#endif

	shared_ptr<InputPolicy<Data>> in(new LimitInput(cin));
	while (in.get() != nullptr && in->hasNext())
	{
		Data data = in->next();
		AlgoPolicy<vector<Fraction>>* algo = new BruteForceAlgo(data.n);
		Solution<vector<Fraction>> solution(algo);
		vector<Fraction> s = solution.run();
		shared_ptr<OutputPolicy> output(new Output(cout, s));
		output->write();
	}

#ifndef ONLINE_JUDGE
	cin.rdbuf(cinback);
	cout.rdbuf(coutback);
#endif

	return 0;
}
