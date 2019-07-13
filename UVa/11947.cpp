#include <cstdio>
#include <string>

using namespace std;

const int MONTH = 12;
const int DAYS[MONTH] = {31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31}; 

int year, month, day;


void input()
{
	scanf("%2d%2d%4d", &month, &day, &year);
}

bool isLeap(int y)
{
	return (y % 4 == 0 && y % 100 != 0) || (y % 400 == 0);
}

void solve()
{
	int total = 280;
	month--;
	
	while (total > 0) {
		if (month == 1) {
			int flag = (isLeap(year) == true);

			if (total < DAYS[month] + flag) {
				day += total;
				if (day > DAYS[month] + flag) {
					day -= DAYS[month] + flag;
					month++;
					if (month >= 12) month -= 12, year++;
				}
				break;
			} else {
				total -= DAYS[month] + flag;
				month++;
				if (month >= 12) month -= 12, year++;
			}
		} else {
			if (total < DAYS[month]) {
				day += total;
				if (day > DAYS[month]) {
					day -= DAYS[month];
					month++;
					if (month >= 12) month -= 12, year++;
				}
				break;
			} else {
				total -= DAYS[month];
				month++;
				if (month >= 12) month -= 12, year++;	 
			}
		}
	}
	
	month++;
	string sign;
	
	if ((month == 1 && day >= 21) || (month == 2 && day <= 19)) sign = "aquarius";
	else if ((month == 2 && day >= 20) || (month == 3 && day <= 20)) sign = "pisces";
	else if ((month == 3 && day >= 21) || (month == 4 && day <= 20)) sign = "aries";
	else if ((month == 4 && day >= 21) || (month == 5 && day <= 21)) sign = "taurus";
	else if ((month == 5 && day >= 22) || (month == 6 && day <= 21)) sign = "gemini";
	else if ((month == 6 && day >= 22) || (month == 7 && day <= 22)) sign = "cancer";
	else if ((month == 7 && day >= 22) || (month == 8 && day <= 21)) sign = "leo";
	else if ((month == 8 && day >= 22) || (month == 9 && day <= 23)) sign = "virgo";
	else if ((month == 9 && day >= 24) || (month == 10 && day <= 23)) sign = "libra";
	else if ((month == 10 && day >= 24) || (month == 11 && day <= 22)) sign = "scorpio";
	else if ((month == 11 && day >= 23) || (month == 12 && day <= 22)) sign = "sagittarius";
	else if ((month == 12 && day >= 23) || (month == 1 && day <= 20)) sign = "capricorn";
	
	printf(" %02d/%02d/%d %s\n", month, day, year, sign.c_str());
}

int main()
{
	int cas;
#ifndef ONLINE_JUDGE
	freopen("./uva_in.txt", "r", stdin);
#endif
	
	scanf("%d", &cas);
	for (int i = 1; i <= cas; i++) {
		input();		
		printf("%d", i);
		solve();
	}
	return 0;
}
