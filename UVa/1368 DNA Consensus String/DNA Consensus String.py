#!/usr/bin/env python3
# -*- coding: utf-8 -*-
"""
Created on Sat Sep 21 07:18:00 2019

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
    def __init__(self, dnas):
        self.dnas = dnas
    
    def process(self):
        m = len(self.dnas[0])
        ans = ''
        cnt = 0
        for i in range(m):
            atgc = {'A':0, 'C':0, 'G':0, 'T':0}
            for dna in self.dnas:
                atgc[dna[i]] += 1
            maxVal = 0
            maxCh = 'A'
            for key in sorted(atgc):
                if atgc[key] > maxVal:
                    maxVal = atgc[key]
                    maxCh = key
            c = 0
            for dna in self.dnas:
                if dna[i] != maxCh:
                    c +=1
            ans += maxCh
            cnt += c
            
        return ans, cnt
    

def makeAlgo(*args):
    name, dnas = args
    if name == "greedy":
        return GreedyAlgo(dnas)
    
    return None

class Solution:
    def __init__(self, algo):
        self.algo = algo
        
    def run(self):
        return self.algo.execute()
    
# sys.stdin = open('/home/wl/OJ/OJ/UVa/uvain')
it = iter(sys.stdin)
testCase = int(next(it).strip())
for _ in range(testCase):
    m, n = map(int, next(it).strip().split())
    dnas = []
    for _ in range(m):
        dna = next(it).strip()
        dnas.append(dna)
    algo = makeAlgo("greedy", dnas)
    solution = Solution(algo)
    ans, cnt = solution.run()
    print("{}".format(ans))
    print("{}".format(cnt))
    
sys.stdin.close()