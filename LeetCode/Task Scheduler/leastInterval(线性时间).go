package main

import (
	"math"
	"sort"
)

const N = 26

func leastInterval(tasks []byte, n int) int {
	algo := &LinearAlgo{}
	return algo.Process(tasks, n)
}

type AlgoStrategy interface {
	Process(tasks []byte, n int) int
}

type LinearAlgo struct {

}

func (p *LinearAlgo) Process(tasks []byte, n int) int {
	c := make([]int, N)

	for _, v := range tasks {
		c[v - 'A']++
	}

	sort.Ints(c)

	maxVal := c[N  - 1] - 1
	maxIdleSlots := maxVal * n

	for i := N - 2; i > 0 && c[i] > 0; i-- {
		maxIdleSlots -= int(math.Min(float64(maxVal), float64(c[i])))
	}

	switch {
	case maxIdleSlots > 0:
		return maxIdleSlots + len(tasks)
	default:
		return len(tasks)
	}
}

