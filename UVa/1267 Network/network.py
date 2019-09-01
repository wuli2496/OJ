# -*- coding: utf-8 -*-
"""
Created on Sun Sep  1 12:19:31 2019

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


class SearchAlgo(AlgoPolicy):
    def __init__(self, n, s, k, graph):
        self.n = n
        self.s = s
        self.k = k
        self.graph = graph
        self.parent = {}
        self.nodes = {}
        self.covered = {}
        
    def process(self):
        self.__dfs(self.s, -1, 0)
        return self.__solve()
    
    def __dfs(self, u, f, dep):
        self.parent[u] = f
        size = len(self.graph[u])
        if size == 1 and dep > self.k:
            if dep not in self.nodes:
                self.nodes[dep] = []
            self.nodes[dep].append(u)
            return
        
        for i in self.graph[u]:
            if i != f:
                self.__dfs(i, u, dep + 1)
                
    def __solve(self):
        ans = 0
        nodes = [self.nodes[key] for key in reversed(sorted(self.nodes.keys()))]
        for node in nodes:
            for vertex in node:
                if vertex in self.covered:
                    continue
                
                p = vertex
                for i in range(self.k):
                    p = self.parent[p]
                self.__dfs2(p, -1, 0)
                ans += 1
        return ans

    def __dfs2(self, u, f, dep):
        self.covered[u] = True
        
        for v in self.graph[u]:
            if v != f and dep < self.k:
                self.__dfs2(v, u, dep +1)


class Solution:
    def __init__(self, algo):
        self.algo = algo
        
    def run(self):
        return self.algo.execute()


# sys.stdin = open(r'D:\program\python\uvain.txt')
it = iter(sys.stdin)
line = it.__next__()
testCase = int(line.strip())
for i in range(testCase):
    graph = {}
    n = int(it.__next__().strip())
    s, k = map(int, it.__next__().strip().split())
    for j in range(n - 1):
        a, b = map(int, it.__next__().strip().split())
        if a not in graph:
            graph[a] = []
        if b not in graph:
            graph[b] = []
        graph[a].append(b)
        graph[b].append(a)
    solution = Solution(SearchAlgo(n, s, k, graph))
    ans = solution.run()
    print("%d" % ans)
