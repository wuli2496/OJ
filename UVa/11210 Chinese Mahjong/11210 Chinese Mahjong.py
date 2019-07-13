import sys
from collections import Counter

mahjang = ["1T", "2T", "3T", "4T", "5T", "6T", "7T", "8T", "9T",
           "1S", "2S", "3S", "4S", "5S", "6S", "7S", "8S", "9S",
           "1W", "2W", "3W", "4W", "5W", "6W", "7W", "8W", "9W",
           "DONG", "NAN", "XI", "BEI",
           "ZHONG", "FA", "BAI"]

#sys.stdin = open(r'F:\OJ\uva_in.txt')

N = 34
M = 3
COUNT = 4
DEPTH = 3


class Solution:
    def __init__(self, mj):
        self.mj = mj

    def solve(self):
        c = Counter()
        for i in range(N):
            c[i] = 0
        c.update(self.mj)

        ans = []
        for i in range(N):
            if c[i] >= COUNT:
                continue
            c[i] += 1
            if self.check(c):
                ans.append(mahjang[i])
            c[i] -= 1

        return ans

    def search(self, dep, c):
        for i in range(N):
            if c[i] >= 3:
                if dep == DEPTH:
                    return True
                c[i] -= 3
                if self.search(dep + 1, c):
                    c[i] += 3
                    return True
                c[i] += 3

        for i in range(N - 9):
            if (i % 9 <= 6) and (c[i] >= 1) and (c[i + 1] >= 1) and (c[i + 2] >= 1):
                if dep == DEPTH:
                    return True
                c[i] -= 1
                c[i + 1] -= 1
                c[i + 2] -= 1
                if self.search(dep + 1, c):
                    c[i] += 1
                    c[i + 1] += 1
                    c[i + 2] += 1
                    return True
                c[i] += 1
                c[i + 1] += 1
                c[i + 2] += 1
        return False

    def check(self, c):
        for i in range(N):
            if c[i] >= 2:
                c[i] -= 2
                if self.search(0, c):
                    c[i] += 2
                    return True
                c[i] += 2
        return False

caseNo = 1

for line in sys.stdin:
    if line[0] == '0':
        break

    tiles = line.strip().split(" ")
    mj = [mahjang.index(tile) for tile in tiles]
    solver = Solution(mj)
    ans = solver.solve()
    print("Case %d: " % caseNo, end='')
    if len(ans) > 0:
        for i in range(len(ans)):
            if i + 1 == len(ans):
                print(ans[i])
            else:
                print(ans[i], end=' ')
    else:
        print("Not ready")
    caseNo += 1

sys.stdin.close()

