#!/usr/bin/env python3
# -*- coding: utf-8 -*-

from abc import ABCMeta, abstractmethod
import sys
import math

class AlgoPolicy(metaclass=ABCMeta):
    def __init__(self):
        pass
    
    @abstractmethod
    def process(self):
        pass
    
    def execute(self):
        return self.process()
    
class BaseAlgo(AlgoPolicy):
    def __init__(self, n):
        self.n = n
        
    def process(self):
        if self.n == 1:
            return 0
        else:
            return int(math.log(self.n - 1, 2)) + 1
    
class Solution:
    def __init__(self, algo):
        self.algo = algo
        
    def run(self):
        return self.algo.execute()
    
# sys.stdin = open('/home/wl/OJ/OJ/UVa/uvain')
it = iter(sys.stdin)
n = int(it.__next__().strip())
testCase = 1
while not (n < 0):
    algo = BaseAlgo(n)
    solution = Solution(algo)
    ans = solution.run()
    print("Case {}: {}".format(testCase, ans))
    n = int(it.__next__().strip())
    testCase += 1
sys.stdin.close()
