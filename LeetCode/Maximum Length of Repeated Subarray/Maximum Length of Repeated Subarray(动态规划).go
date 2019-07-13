package main

import (
	math"
)

//时间复杂度O(m*n)

func findLength(A []int, B []int) int {
	var algo AlgoStrategy
	algo = &DynamicProgrammingAlgo{A, B}
	return algo.Execute()
}

type AlgoStrategy interface {
	Execute() int
}

type DynamicProgrammingAlgo struct {
	a []int
	b []int
}

func (algo *DynamicProgrammingAlgo) Execute() int {
	a := algo.a
	b := algo.b
	
	f := make([][]int, len(a) + 1)
	for i := 0; i < len(f); i++ {
		f[i] = make([]int, len(b) + 1)
	}
	
	ans := 0
	for i := len(a) - 1; i >= 0; i-- {
		for j := len(b) - 1; j >= 0; j-- {
			if a[i] == b[j] {
				f[i][j] = f[i + 1][j + 1] + 1
				ans = int(math.Max(float64(ans), float64(f[i][j])))
			}
		}
	}
	
	return ans
} 
