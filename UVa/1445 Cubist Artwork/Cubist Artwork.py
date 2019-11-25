#!/usr/bin/env python3
# -*- coding: utf-8 -*-
"""
Created on Mon Nov 25 22:54:26 2019

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
    def __init__(self, frontView, sideView):
        self.frontView = frontView
        self.sideView = sideView
    
    def process(self):
        di = dict()
        ans = 0
        for front in self.frontView:
            if front in di:
                di[front] += 1
            else:
                di[front] = 1
            ans += front
            
        for side in self.sideView:
            if side in di:
                di[side] -= 1
                if di[side] == 0:
                    del di[side]
            else:
                ans += side
                
        return ans

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
    w, h = map(int, next(ite).strip().split())
    if w == 0 and h == 0:
        break
    
    frontView = map(int, next(ite).strip().split())
    sideView = map(int, next(ite).strip().split())
    
    algo = makeAlgo('greedy', frontView, sideView)
    solution = Solution(algo)
    ans = solution.run()
    print('{}'.format(ans))
sys.stdin.close()