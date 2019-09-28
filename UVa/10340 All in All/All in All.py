#!/usr/bin/env python3
# -*- coding: utf-8 -*-
"""
Created on Fri Sep 27 06:39:36 2019

@author: wl
"""
from abc import ABCMeta, abstractmethod
import sys

class AlgoPolicy(metaclass=ABCMeta):
    def __init__(self):
        pass
    
    @abstractmethod
    def process(self):
        assert False, 'should implement process'
        
    def execute(self):
        return self.process()
    
class GreedyAlgo(AlgoPolicy):
    def __init__(self, s, t):
        self.s = s
        self.t = t
        
    def process(self):
        sLen = len(self.s)
        tLen = len(self.t)
        i = 0
        j = 0
        for j in range(tLen):
            if i >= sLen:
                break
            else:
                if self.s[i] == self.t[j]:
                    i += 1
        
        if i == sLen:
            return 'Yes'
        
        return 'No'
    
def makeAlgo(*args):
    name, *arg = args
    if name == 'greedy':
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
    try:
        s, t = next(ite).strip().split()
        algo = makeAlgo('greedy', s, t)
        solution = Solution(algo)
        ans = solution.run()
        print('{}'.format(ans))
    except StopIteration:
        break
sys.stdin.close()
