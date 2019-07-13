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


class RecurAlgo(AlgoBase):
    def __init__(self, start, end):
        AlgoBase.__init__(self)
        self.start = start
        self.end = end

    def process(self):
        pos = -1
        for i in range(len(start) - 1, -1, -1):
            if start[i] != end[i]:
                pos = i
                break

        if -1 == pos:
            return 0

        finish = 5 - start[pos] - end[pos]
        return self.recursion(start, pos - 1, finish) + self.recursion(end, pos - 1, finish) + 1

    def recursion(self, f, pos, finish):
        if -1 == pos:
            return 0
        if (f[pos] - 1) == finish:
            return self.recursion(f, pos - 1, finish)
        else:
            return self.recursion(f, pos - 1, 4 - f[pos] - finish) + 2 ** pos


# sys.stdin = open(r'F:\OJ\uva_in.txt')
case = 1
for line in sys.stdin:
    n = int(line.strip())
    if 0 == n:
        break
    start = sys.stdin.__next__().strip()
    end = sys.stdin.__next__().strip()
    start = list(map(int, start.split(' ')))
    end = list(map(int, end.split(' ')))
    algo = RecurAlgo(start, end)
    ans = algo.execute()
    print("Case %d: %d" % (case, ans))
    case += 1

sys.stdin.close()

