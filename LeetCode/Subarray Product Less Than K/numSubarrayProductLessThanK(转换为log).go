package main

import "math"

func numSubarrayProductLessThanK(nums []int, k int) int {
	algo := &LogTransAlgoStrategy{nums, k}
	return algo.Execute()
}

type AlgoStrategy interface {
	Execute() int
}

type LogTransAlgoStrategy struct {
	nums []int
	k int
}

func (algo *LogTransAlgoStrategy) Execute() int {
	nums := algo.nums
	k := algo.k

	numsLen := len(nums)
	logValue := make([]float64, numsLen + 1)
	for i := 0; i < numsLen; i++ {
		logValue[i + 1] = logValue[i] + math.Log(float64(nums[i]))
	}

	logValueLen := len(logValue)
	ans := 0
	for i := 0; i < logValueLen; i++ {
		lo := i + 1
		hi := logValueLen
		for lo < hi {
			mi := (lo + hi) >> 1
			if logValue[mi] < logValue[i] + math.Log(float64(k)) {
				lo = mi + 1
			} else {
				hi = mi
			}
		}

		ans += lo - i - 1
	}

	return ans
}

