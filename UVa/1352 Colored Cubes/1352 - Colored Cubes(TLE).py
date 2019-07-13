import sys

dice24 = [[3, 1, 0, 5, 4, 2],
[3, 5, 1, 4, 0, 2],
[3, 4, 5, 0, 1, 2],
[3, 0, 4, 1, 5, 2],
[1, 2, 0, 5, 3, 4],
[5, 2, 1, 4, 3, 0],
[4, 2, 5, 0, 3, 1],
[0, 2, 4, 1, 3, 5],
[0, 1, 2, 3, 4, 5],
[1, 5, 2, 3, 0, 4],
[5, 4, 2, 3, 1, 0],
[4, 0, 2, 3, 5, 1],
[5, 1, 3, 2, 4, 0],
[4, 5, 3, 2, 0, 1],
[0, 4, 3, 2, 1, 5],
[1, 0, 3, 2, 5, 4],
[4, 3, 0, 5, 2, 1],
[0, 3, 1, 4, 2, 5],
[1, 3, 5, 0, 2, 4],
[5, 3, 4, 1, 2, 0],
[2, 4, 0, 5, 1, 3],
[2, 0, 1, 4, 5, 3],
[2, 1, 5, 0, 4, 3],
[2, 5, 4, 1, 0, 3]
]


class Solution:
    def __init__(self, data):
        self.data = data
        self.ans = 6 * len(self.data)

    def solve(self):
        res = [0] * len(self.data)
        self.dfs(1, res)

        return self.ans

    def check(self, res):
        color = [[0 for i in range(6)] for j in range(len(self.data))]
        for i in range(len(res)):
            for j in range(6):
                color[i][dice24[res[i]][j]] = self.data[i][j]
        tot = 0
        for i in range(6):
            maxface = 0
            cnt = [0 for i in range(6 * len(self.data))]
            for j in range(len(self.data)):
                cnt[color[j][i]] = cnt[color[j][i]] + 1
                maxface = max(maxface, cnt[color[j][i]])

            tot += len(self.data) - maxface
        self.ans = min(self.ans, tot)

    def dfs(self, cur_depth, res):
        if cur_depth == len(self.data):
            self.check(res)
        else:
            for i in range(len(dice24)):
                res[cur_depth] = i
                self.dfs(cur_depth + 1, res)

#sys.stdin = open(r'F:\OJ\uva_in.txt', 'r')
while True:
    n = int(sys.stdin.readline().strip())
    if n == 0:
        break

    datas = []
    colorDict = {}
    for i in range(n):
        line = sys.stdin.readline().strip()
        colors = line.split(" ")
        data = []
        for color in colors:
            if color in colorDict:
                data.append(colorDict[color])
            else:
                size = len(colorDict)
                colorDict[color] = size
                data.append(size)
        datas.append(data)

    a = Solution(datas)
    ans = a.solve()
    print(ans)

sys.stdin.close()


