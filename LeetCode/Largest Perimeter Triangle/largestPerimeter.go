package main

import "sort"

func largestPerimeter(A []int) int {
	algo := &GreedyAlgo{A}
	return algo.Execute()
}

type AlgoStrategy interface {
	Execute() int
}

type GreedyAlgo struct {
	a []int
}

func (algo *GreedyAlgo) Execute() int {
	a := algo.a

	sort.Ints(a)

	aLen := len(a)
	for i := aLen - 3; i >= 0; i-- {
		sum := a[i] + a[i + 1]
		if sum > a[i + 2] {
			return sum + a[i + 2]
		}
	}

	return 0
}

