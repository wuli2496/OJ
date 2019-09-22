#!/usr/bin/env python3
# -*- coding: utf-8 -*-
"""
Created on Fri Sep 13 08:42:00 2019

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
        return self.process();
    
class BaseAlgo(AlgoPolicy):
    def __init__(self, a, b):
        self.a = a
        self.b = b
    
    def process(self):
        cnta = self.__cal(self.a)
        cntb = self.__cal(self.b)
        return 'YES' if cnta == cntb else 'NO'
    
    def __cal(self, a):
        cnt = [0 for i in range(26)]
        for ch in a:
            cnt[ord(ch) - ord('A')] += 1
        return sorted(cnt)
    
class Solution:
    def __init__(self, algo):
        self.algo = algo
        
    def run(self):
        return self.algo.execute()
  
def makeAlgo(s, a, b):
    if s == "base":
        return BaseAlgo(a, b)
    
    return None

# sys.stdin = open("/home/wl/OJ/OJ/UVa/uvain")
it = iter(sys.stdin)
while True: 
    try:
        a = next(it).strip()
        b = next(it).strip()
        algo = makeAlgo("base", a, b)
        solution = Solution(algo)
        ans = solution.run()
        print("{}".format(ans))
    except StopIteration:
        break
sys.stdin.close()