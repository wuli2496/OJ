#include <iostream>
#include <fstream>

using namespace std;

int main()
{
#ifndef ONLINE_JUDGE
	ifstream fin("f:\\OJ\\uva_in.txt");
	streambuf *old = cin.rdbuf(fin.rdbuf());
#endif

	
#ifndef ONLINE_JUDGE
	cin.rdbuf(old);
#endif
	return 0;
}