from abc import ABCMeta, abstractmethod
import sys


class AlgoBase(metaclass=ABCMeta):
    def __init__(self):
        pass

    @abstractmethod
    def process(self):
        assert("implement process")

    def execute(self):
        return self.process()


class AlgoImpl(AlgoBase):
    def __init__(self, grid, n):
        self.grid = grid
        self.n = n

    def process(self):
        for i in range(self.n):
            for j in range(self.n):
                if self.grid[i][j] == '.':
                    self.check(i, j)

        ans = []
        for i in range(n):
            ans.append(''.join(self.grid[i]))
        return ans

    def check(self, i, j):
        for c in range(ord('A'), ord('Z') + 1):
            if i - 1 >= 0 and self.grid[i - 1][j] == chr(c):
                continue
            if i + 1 < self.n and self.grid[i + 1][j] == chr(c):
                continue;
            if j - 1 >= 0 and self.grid[i][j - 1] == chr(c):
                continue
            if j + 1 < self.n and self.grid[i][j + 1] == chr(c):
                continue

            self.grid[i][j] = chr(c)
            break


def output(ans):
    for i in range(len(ans)):
        print(ans[i])


# sys.stdin = open(r'F:\OJ\uva_in.txt')
it = iter(sys.stdin)
line = it.__next__()
testCase = int(line.strip())
for i in range(testCase):
    n = int(it.__next__().strip())
    grid = []
    for j in range(n):
        grid.append(list(it.__next__().strip()))
    algoimpl = AlgoImpl(grid, n)
    ans = algoimpl.execute()
    print("Case %d:" % (i + 1))
    output(ans)
# sys.stdin.close()


