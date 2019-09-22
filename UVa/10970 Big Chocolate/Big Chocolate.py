#!/usr/bin/env python3
# -*- coding: utf-8 -*-
"""
Created on Sat Sep 21 20:51:23 2019

@author: wl
"""
from abc import ABCMeta, abstractmethod
import sys

class AlgoPolicy(metaclass=ABCMeta):
    def __init__(self):
        pass
    
    @abstractmethod
    def process(self):
        pass
    
    def execute(self):
        return self.process()
    

class GreedyAlgo(AlgoPolicy):
    def __init__(self, m, n):
        self.m = m
        self.n = n
        
    def process(self):
        return self.m * self.n - 1
  
def makeAlgo(*args):
    name, *arg = args
    if name == "greedy":
        return GreedyAlgo(*arg)
    
    return None

class Solution:
    def __init__(self, algo):
        self.algo = algo
        
    def run(self):
        return self.algo.execute()
    
# sys.stdin = open('/home/wl/OJ/OJ/UVa/uvain')
it = iter(sys.stdin)
while True:
    try:
        m, n = map(int, next(it).strip().split())
        algo = makeAlgo('greedy', m, n)
        solution = Solution(algo)
        ans = solution.run()
        print('{}'.format(ans))
    except StopIteration:
        break
sys.stdin.close()
