#!/usr/bin/env python3
# -*- coding: utf-8 -*-
"""
Created on Sat Sep 28 15:17:48 2019

@author: wl
"""
from abc import ABCMeta, abstractmethod
import sys
import math

class AlgoPolicy(metaclass=ABCMeta):
    def __init__(self):
        pass
    
    @abstractmethod
    def process(self):
        assert False, 'implement the method'
        
    def execute(self):
        return self.process()
    
class GreedyAlgo(AlgoPolicy):
    def __init__(self, l, sprinkler):
        self.l = l
        self.sprinkler = sprinkler
        
    def process(self):
        #print('{}'.format(self.sprinkler))
        size = len(self.sprinkler)
        if size == 0:
            return -1
        
        if self.sprinkler[0][0] > 0:
            return -1
        
        left = 0
        right = 0
        i = 0
        cnt = 0
        found = False
        while i < size:
            j = i
            while j < size and self.sprinkler[j][0] <= left:
                if self.sprinkler[j][1] > right:
                    right = self.sprinkler[j][1]
                j += 1
            if j == i:
                break
            cnt += 1
            i = j
            left = right
            if left >= self.l:
                found = True
                break
        
        if found:
            return cnt
        else:
            return -1
            
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
        sprinkler = []
        n, l, w = map(int, next(ite).strip().split())
        for _ in range(n):
            x, r = map(int, next(ite).strip().split())
            if r <= w / 2:
                continue
            r1 = math.sqrt(r ** 2 - (w / 2) ** 2)
            left = x - r1
            right = x + r1
            if right <= 0:
                continue
            if left >= l:
                continue
            sprinkler.append((left, right))
        sprinkler.sort()
        algo = makeAlgo('greedy', l, sprinkler)
        solution = Solution(algo)
        ans = solution.run()
        print('{}'.format(ans))
    except StopIteration:
        break
sys.stdin.close()

