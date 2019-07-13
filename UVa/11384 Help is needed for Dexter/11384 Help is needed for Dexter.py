import sys


class AlgoBase:
    def __init__(self):
        pass

    def execute(self):
        return self.process()

class RecurAlgo(AlgoBase):
    def __init__(self, n):
        AlgoBase.__init__(self)
        self.n = n

    def process(self):
        return self.dfs(self.n)

    def dfs(self, n):
        if n == 1:
            return 1
        else:
            return self.dfs(n // 2) + 1



#sys.stdin = open(r'F:\OJ\uva_in.txt')


for line in sys.stdin:
    n = int(line.strip())
    algo = RecurAlgo(n)
    ans = algo.execute()
    print(ans)


sys.stdin.close()

