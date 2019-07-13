package main

import "sort"

func sortedSquares(A []int) []int {
	algo := &GreedyAlgo{A}
	return algo.Execute()
}

type AlgoStrategy interface {
	Execute() []int
}

type GreedyAlgo struct {
	a []int
}

func (algo *GreedyAlgo) Execute() []int {
	a := algo.a

	sort.Ints(a)

	aLen := len(a)
	for i := 0; i < aLen; i++ {
		a[i] = a[i] * a[i]
	}

	sort.Ints(a)

	return a 
}

