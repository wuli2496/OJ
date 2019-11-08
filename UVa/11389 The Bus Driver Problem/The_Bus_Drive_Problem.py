#!/usr/bin/env python3
# -*- coding: utf-8 -*-
"""
Created on Fri Nov  8 20:50:49 2019

@author: wl
"""
from abc import ABCMeta, abstractmethod
import sys

class AlgoPolicy(metaclass=ABCMeta):
    def __init__(self):
        pass
    
    def execute(self):
        return self.process()
    
    @abstractmethod
    def process(self):
        assert False, 'should implement process'
        
class GreedyAlgo(AlgoPolicy):
    def __init__(self, mornings, afternoons, d, r):
        self.mornings = mornings
        self.afternoons = afternoons
        self.d = d
        self.r = r
        
    def process(self):
        self.mornings.sort()
        self.afternoons.sort(reverse=True)

        ans = 0
        i = 0
        for morning in self.mornings:
            afternoon = self.afternoons[i]
            tmp = morning + afternoon
            if tmp > self.d:
                ans += self.r * (tmp - self.d)
            i += 1
                
        return ans
    
def makeAlgo(*args):
    algoName, *arg = args
    if algoName == 'greedy':
        return GreedyAlgo(*arg)
    
    return None


class Solution:
    def __init__(self, algo):
        self.algo = algo
        
    def run(self):
        return self.algo.execute()

# sys.stdin = open('/home/wl/OJ/OJ/UVa/uvain')
ite = iter(sys.stdin)
while True:
    n, d, r = list(map(int, next(ite).strip().split()))
    if n == 0 and d == 0 and r == 0:
        break
    
    mornings = list(map(int, next(ite).strip().split()))
    afternoons = list(map(int, next(ite).strip().split()))
    
    algo = makeAlgo('greedy', mornings, afternoons, d, r)
    solution = Solution(algo)
    ans = solution.run()
    print("{}".format(ans))

        
        
        