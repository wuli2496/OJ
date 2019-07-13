from abc import ABCMeta, abstractmethod
import sys
import math


class AlgoBase(metaclass=ABCMeta):
    def __init__(self):
        pass

    @abstractmethod
    def process(self):
        pass

    def execute(self):
        return self.process()


class BinarySearchAlgo(AlgoBase):
    def __init__(self, n, f, r):
        AlgoBase.__init__(self)
        self.n = n
        self.f = f
        self.r = r

    def process(self):
        low = 0
        r = max(self.r)
        high = r * r * math.pi
        while high - low > 1e-5:
            mid = (low + high) / 2
            if self.check(mid):
                low = mid
            else:
                high = mid
        return low

    def check(self, area):
        s = 0
        for i in self.r:
            s += math.floor(math.pi * i * i / area)
        return s >= self.f + 1


# sys.stdin = open(r'F:\OJ\uva_in.txt')
it = iter(sys.stdin)
line = it.__next__()
testCase = int(line.strip())
while True:
    if testCase == 0:
        break

    line = it.__next__().strip()
    n, f = list(map(int, line.split(' ')))
    line = it.__next__().strip()
    r = list(map(int, line.split(' ')))
    algo = BinarySearchAlgo(n, f, r)
    ans = algo.execute()
    print("%.4f" % ans)
    testCase -= 1

sys.stdin.close()

