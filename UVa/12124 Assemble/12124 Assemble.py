from abc import ABCMeta, abstractmethod
import sys


class AlgoBase(metaclass=ABCMeta):
    def __init__(self):
        pass

    @abstractmethod
    def process(self):
        pass

    def execute(self):
        return self.process()


class BinarySearchAlgo(AlgoBase):
    def __init__(self, n, b, data, maxquality):
        AlgoBase.__init__(self)
        self.n = n
        self.b = b
        self.data = data
        self.maxquality = maxquality

    def process(self):
        low = 0; high = self.maxquality
        while low < high:
            mid = (low + high + 1) // 2
            if self.check(mid):
                low = mid
            else:
                high = mid - 1
        return low

    def check(self, curquality):
        s = 0
        for l in self.data.values():
            cheapest = self.b + 1
            for (a, b) in l:
                if b >= curquality:
                    cheapest = min(cheapest, a)
            if cheapest == self.b + 1:
                return False
            s += cheapest
            if s > self.b:
                return False
        return True


# sys.stdin = open(r'F:\OJ\uva_in.txt')
it = iter(sys.stdin)
line = it.__next__()
testCase = int(line.strip())
while True:
    if testCase == 0:
        break

    line = it.__next__().strip()
    n, b = list(map(int, line.split(' ')))

    componentsDict = dict()
    maxQuality = 0
    for i in range(n):
        line = it.__next__().strip()
        line = line.split()
        maxQuality = max(maxQuality, int(line[3]))
        if line[0] not in componentsDict:
            componentsDict[line[0]] = []
        componentsDict[line[0]].append(list(map(int, line[2:])))
    algo = BinarySearchAlgo(n, b, componentsDict, maxQuality)
    ans = algo.execute()
    print(ans)
    testCase -= 1

sys.stdin.close()

