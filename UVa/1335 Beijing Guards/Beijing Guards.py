#!/usr/bin/env python3
# -*- coding: utf-8 -*-
"""
Created on Sun Sep  1 06:29:28 2019

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


class BinarySearchAlgo(AlgoPolicy):
    def __init__(self, n, awards):
        self.n = n
        self.awards = awards
        
    def process(self):
        return self.__search()
    
    def __search(self):
        size = len(self.awards)
        if size == 1:
            return self.awards[0]
        
        l = 0
        r = 0
        for i in range(size):
            if i != size - 1:
                l = max(l, self.awards[i] + self.awards[i + 1])
            else:
                l = max(l, self.awards[i] + self.awards[0])
        
        if size % 2 == 1:
            for i in range(size):
                r = max(r, self.awards[i] * 3)
            while l < r:
                m = l + (r - l) // 2
                if self.__check(m):
                    r = m
                else:
                    l = m + 1
    
        return l
    
    def __check(self, m):
        size = len(self.awards)
        left = [0 for i in range(size)]
        right = [0 for i in range(size)]
        
        x = self.awards[0]
        y = m - x
        left[0] = self.awards[0]
        right[0] = 0
        for i in range(1, size):
            if i == 1:
                left[1] = 0
                right[1] = self.awards[1]
                continue
                
            if i % 2 == 0:
                right[i] = min(self.awards[i], y - right[i - 1])
                left[i] = self.awards[i] - right[i]
            else:
                left[i] = min(self.awards[i], x - left[i - 1])
                right[i] = self.awards[i] - left[i]
                
        return left[size - 1] == 0
        
            
        
class Solution:
    def __init__(self, algo):
        self.algo = algo
        
    def run(self):
        return self.algo.execute()
    
# sys.stdin = open("/home/wl/OJ/OJ/UVa/uvain")
it = iter(sys.stdin)
line = it.__next__()
n = int(line.strip())
while n != 0:
    awards = []
    for i in range(n):
        award = int(it.__next__().strip())
        awards.append(award)
    algo = BinarySearchAlgo(n, awards)
    solution = Solution(algo)
    ans = solution.run()
    print("%d" % (ans))
    n = int(it.__next__().strip())    
sys.stdin.close()    

    

        
