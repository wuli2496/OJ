#!/usr/bin/env python3
# -*- coding: utf-8 -*-
"""
Created on Sat Nov 16 08:48:03 2019

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
        
class ConstructAlgo(AlgoPolicy):
    def __init__(self, n):
        self.n = n
        
    def process(self):
        if self.n <= 3:
            return 1
        elif self.n == 4:
            return 2
        else:
            return self.n
    
def makeAlgo(*args):
    name, *arg = args
    if name == 'construct':
        return ConstructAlgo(*arg)
    
    return None

class Solution:
    def __init__(self, algo):
        self.algo = algo
        
    def run(self):
        return self.algo.execute()
    
# sys.stdin = open('/home/wl/OJ/OJ/UVa/uvain')
ite = iter(sys.stdin)
while True:
    n = int(next(ite).strip())
    if n == 0:
        break
    
    algo = makeAlgo('construct', n)
    solution = Solution(algo)
    ans = solution.run()
    print('{}'.format(ans))
sys.stdin.close()
    
    
