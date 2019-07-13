package main

//时间复杂度为O(m*n*min(m,n))
import "math"

func findLength(A []int, B []int) int {
    algo := &BruteForceAlgo{A, B}
    return algo.Execute()
}

type AlgoStrategy interface {
	Execute() int
}

type BruteForceAlgo struct {
	a []int
	b []int
}

func (algo *BruteForceAlgo) Execute() int {
	a := algo.a
	b := algo.b 
	
	ans := 0
	for i := 0; i < len(a); i++ {
		for j := 0; j < len(b); j++ {
			k := 0
			for i + k < len(a) && j + k < len(b) && a[i + k] == b[j + k] {
				k++
			}
			ans = int(math.Max(float64(ans), float64(k)))
		}
	}
	
	return ans
}
