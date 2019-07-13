package main

import "math"

func minCostClimbingStairs(cost []int) int {
	f0 := 0
	f1 := 0
	arLen := len(cost)
	for i := arLen - 1; i >= 0; i--{
		f2 := cost[i] + int(math.Min(float64(f0), float64(f1)))
		f0 = f1
		f1 = f2
	}

	return int(math.Min(float64(f0), float64(f1)))
}
